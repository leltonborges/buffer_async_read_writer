package org.project.async.buffer.batch.async.file.fixed.config

import org.project.async.buffer.batch.JobAbstract
import org.project.async.buffer.batch.utils.BatchUtils
import org.project.async.buffer.config.property.ThreadProperties
import org.project.async.buffer.core.pattern.dto.PersonDTO
import org.project.async.buffer.core.pattern.vo.PersonVO
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
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
import org.springframework.transaction.PlatformTransactionManager
import java.util.concurrent.Future


@Configuration
class JobFileFixedConfig : JobAbstract() {

    @Bean("jobFileFixed")
    fun jobBufferAsync(
        jobRepository: JobRepository,
        @Qualifier("stepBufferFileFixedAsync")
        step: Step,
    ): Job {
        return JobBuilder("JOB_ASYNC_BUFFER_FIXED", jobRepository).start(step).incrementer(RunIdIncrementer()).build()
    }

    @JobScope
    @Bean("stepBufferFileFixedAsync")
    fun stepBufferAsync(
        @Qualifier("fixedStepReader")
        itemReader: ItemReader<PersonDTO>,
        @Qualifier("fixedStepProcessor")
        itemProcessor: ItemProcessor<PersonDTO, PersonVO>,
        @Qualifier("fixedStepWriter")
        itemWriter: ItemWriter<PersonVO>,
        @Qualifier("batchTransactionManager")
        transactionManager: PlatformTransactionManager,
        jobRepository: JobRepository,
        threadProperties: ThreadProperties,
        header: FileFixedFooter,
    ): Step {
        return StepBuilder("STEP_ASYNC_BUFFER_FIXED", jobRepository)
                .chunk<PersonDTO, Future<PersonVO>>(10, transactionManager)
                .reader(itemReader)
                .processor(itemProcessorFixedAsync(itemProcessor, threadProperties))
                .writer(itemWriterFixedAsync(itemWriter))
                .listener(header)
                .build()
    }

    companion object {
        @JvmStatic
        private fun itemProcessorFixedAsync(
            itemProcessor: ItemProcessor<PersonDTO, PersonVO>,
            threadProperties: ThreadProperties,
            namePrefix: String = "t-async-P-",
        ): ItemProcessor<PersonDTO, Future<PersonVO>> {
            val asyncItemProcessor = AsyncItemProcessor<PersonDTO, PersonVO>()
            val taskExecutor = BatchUtils.asyncExecutorProcessor(threadProperties, namePrefix)
            asyncItemProcessor.setTaskExecutor(taskExecutor)
            asyncItemProcessor.setDelegate(itemProcessor)
            return asyncItemProcessor;
        }

        @JvmStatic
        private fun itemWriterFixedAsync(
            itemWriter: ItemWriter<PersonVO>,
        ): ItemWriter<Future<PersonVO>> {
            val asyncItemWriter = AsyncItemWriter<PersonVO>()
            asyncItemWriter.setDelegate(itemWriter)
            return asyncItemWriter
        }
    }

}