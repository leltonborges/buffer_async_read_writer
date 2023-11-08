package org.project.async.buffer.batch.async.file.fixed.config

import org.project.async.buffer.batch.JobAbstract
import org.project.async.buffer.core.pattern.dto.PersonDTO
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.integration.async.AsyncItemProcessor
import org.springframework.batch.integration.async.AsyncItemWriter
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.transaction.PlatformTransactionManager


@Configuration
class JobFileFixedConfig : JobAbstract() {

    @Bean("jobFileFixed")
    fun jobBufferAsync(
        jobRepository: JobRepository,
        @Qualifier("stepBufferFileFixedAsync") step: Step,
    ): Job {
        return JobBuilder("JOB_ASYNC_BUFFER_FIXED", jobRepository)
                .start(step)
                .incrementer(RunIdIncrementer()) //            .incrementer(jobParametersIncrementer())
                .build()
    }

    @Bean("stepBufferFileFixedAsync")
    @JobScope
    fun stepBufferAsync(
            @Qualifier("fixedStepReader") itemReader: ItemReader<PersonDTO>,
            @Qualifier("fixedStepProcessor") itemProcessor: ItemProcessor<PersonDTO, PersonDTO>,
            @Qualifier("fixedStepWriter") itemWriter: ItemWriter<PersonDTO>,
            @Qualifier("batchTransactionManager") transactionManager: PlatformTransactionManager,
            jobRepository: JobRepository,
            header: FileFixedFooter,
    ): Step {
        return StepBuilder("STEP_ASYNC_BUFFER_FIXED", jobRepository)
                .chunk<PersonDTO, PersonDTO>(10, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .listener(header)
                .build()
    }

    @Bean("itemProcessorFixedAsync") 
    @StepScope 
    fun itemProcessorFixedAsync(
            @Qualifier("fixedStepProcessor") itemProcessor: ItemProcessor<PersonDTO, PersonDTO>,
            @Qualifier("taskExecutor") threadPoolTaskExecutor: ThreadPoolTaskExecutor
    ): AsyncItemProcessor<PersonDTO, PersonDTO> {
        val asyncItemProcessor = AsyncItemProcessor<PersonDTO, PersonDTO>()
        asyncItemProcessor.setTaskExecutor(threadPoolTaskExecutor)
        asyncItemProcessor.setDelegate(itemProcessor)
        return asyncItemProcessor;
    }

    @Bean("itemWriterFixedAsync")
    @StepScope
    fun itemWriterFixedAsync(
            @Qualifier("fixedStepWriter") itemWriter: ItemWriter<PersonDTO>,
    ): AsyncItemWriter<PersonDTO> {
        val asyncItemWriter = AsyncItemWriter<PersonDTO>()
        asyncItemWriter.setDelegate(itemWriter)
        return asyncItemWriter
    }

}