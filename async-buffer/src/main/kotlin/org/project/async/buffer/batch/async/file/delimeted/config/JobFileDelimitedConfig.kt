package org.project.async.buffer.batch.async.file.delimeted.config

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
class JobFileDelimitedConfig : JobAbstract() {

    @Bean("jobFileDelimited")
    fun jobBufferAsync(
        jobRepository: JobRepository,
        @Qualifier("stepBufferAsync") step: Step,
    ): Job {
        return JobBuilder("JOB_ASYNC_BUFFER", jobRepository)
                .start(step)
                .incrementer(RunIdIncrementer()) //            .incrementer(jobParametersIncrementer())
                .build()
    }

    @Bean("stepBufferAsync")
    @JobScope
    fun stepBufferAsync(
            @Qualifier("delimitedStepReaderBuffer") itemReader: ItemReader<PersonDTO>,
            @Qualifier("delimitedStepProcessorBuffer") itemProcessor: ItemProcessor<PersonDTO, PersonDTO>,
            @Qualifier("delimitedStepWriterBuffer") itemWriter: ItemWriter<PersonDTO>,
            @Qualifier("batchTransactionManager") transactionManager: PlatformTransactionManager,
            jobRepository: JobRepository,
            header: FileDelimitedFooter,
    ): Step {
        return StepBuilder("STEP_ASYNC_BUFFER", jobRepository)
                .chunk<PersonDTO, PersonDTO>(10, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .listener(header)
                .build()
    }

    @Bean("itemProcessorBufferAsync")
    @StepScope
    fun itemProcessorBufferAsync(
            @Qualifier("delimitedStepProcessorBuffer") itemProcessor: ItemProcessor<PersonDTO, PersonDTO>,
            @Qualifier("taskExecutor") threadPoolTaskExecutor: ThreadPoolTaskExecutor
    ): AsyncItemProcessor<PersonDTO, PersonDTO> {
        val asyncItemProcessor = AsyncItemProcessor<PersonDTO, PersonDTO>()
        asyncItemProcessor.setTaskExecutor(threadPoolTaskExecutor)
        asyncItemProcessor.setDelegate(itemProcessor)
        return asyncItemProcessor;
    }

    @Bean("itemWriterBufferAsync")
    @StepScope
    fun itemWriterBufferAsync(
            @Qualifier("delimitedStepWriterBuffer") itemWriter: ItemWriter<PersonDTO>,
    ): AsyncItemWriter<PersonDTO> {
        val asyncItemWriter = AsyncItemWriter<PersonDTO>()
        asyncItemWriter.setDelegate(itemWriter)
        return asyncItemWriter
    }
}