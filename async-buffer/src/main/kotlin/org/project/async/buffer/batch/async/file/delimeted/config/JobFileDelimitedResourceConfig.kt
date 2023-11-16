package org.project.async.buffer.batch.async.file.delimeted.config

import org.project.async.buffer.batch.JobAbstract
import org.project.async.buffer.batch.utils.BatchUtils
import org.project.async.buffer.config.property.ThreadProperties
import org.project.async.buffer.core.model.buffer.Person
import org.project.async.buffer.core.pattern.dto.PersonDTO
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
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.transaction.PlatformTransactionManager
import java.util.concurrent.Future

@Configuration
class JobFileDelimitedResourceConfig : JobAbstract() {

    @Bean("jobFileDelimitedDB")
    fun jobBufferAsync(
        jobRepository: JobRepository,
        @Qualifier("stepAsyncDelimitedDB")
        step: Step,
    ): Job {
        return JobBuilder("JOB_ASYNC_BUFFER_RESOURCE", jobRepository)
                .start(step)
                .incrementer(RunIdIncrementer())
                .build()
    }

    @Bean("stepAsyncDelimitedDB")
    @JobScope
    fun stepBufferAsyncResource(
        @Qualifier("stepAsyncDelimitedReaderDB")
        itemReader: ItemReader<PersonDTO>,
        @Qualifier("stepAsyncDelimitedProcessorDB")
        itemProcessor: ItemProcessor<PersonDTO, Person>,
        @Qualifier("stepAsyncDelimitedWriterDB")
        itemWriter: ItemWriter<Person>,
        @Qualifier("batchTransactionManager")
        transactionManager: PlatformTransactionManager,
        @Qualifier("taskExecutor")
        threadPoolTaskExecutor: ThreadPoolTaskExecutor,
        threadProperties: ThreadProperties,
        jobRepository: JobRepository,
    ): Step {
        return StepBuilder("STEP_ASYNC_BUFFER_RESOURCE", jobRepository)
            .chunk<PersonDTO, Future<Person>>(this.chuckDefault, transactionManager)
                .reader(itemReader)
                .processor(itemProcessorBufferAsync(itemProcessor, threadProperties))
                .writer(itemWriterBufferAsync(itemWriter))
                .build()
    }

    companion object {
        @JvmStatic
        private fun itemProcessorBufferAsync(
            itemProcessor: ItemProcessor<PersonDTO, Person>,
            threadProperties: ThreadProperties,
            namePrefix: String = "t-async-P-"
        ): ItemProcessor<PersonDTO, Future<Person>> {
            val asyncItemProcessor = AsyncItemProcessor<PersonDTO, Person>()
            val taskExecutor = BatchUtils.asyncExecutorProcessor(threadProperties, namePrefix)
            asyncItemProcessor.setTaskExecutor(taskExecutor)
            asyncItemProcessor.setDelegate(itemProcessor)
            return asyncItemProcessor;
        }

        @JvmStatic
        private fun itemWriterBufferAsync(
            itemWriter: ItemWriter<Person>,
        ): ItemWriter<Future<Person>> {
            val asyncItemWriter = AsyncItemWriter<Person>()
            asyncItemWriter.setDelegate(itemWriter)
            return asyncItemWriter
        }
    }
}