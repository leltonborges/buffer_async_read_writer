package org.project.async.buffer.batch.async.db.delimeted.config

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
import org.springframework.transaction.PlatformTransactionManager
import java.util.concurrent.Future


@Configuration
class JobFileDelimitedConfig : JobAbstract() {

    @Bean("jobFileDelimited")
    fun jobBufferAsync(
        jobRepository: JobRepository,
        @Qualifier("stepBufferAsync")
        step: Step,
    ): Job {
        return JobBuilder("JOB_ASYNC_BUFFER", jobRepository)
                .start(step)
                .incrementer(RunIdIncrementer()) //            .incrementer(jobParametersIncrementer())
                .build()
    }

    @Bean("stepBufferAsync")
    @JobScope
    fun stepBufferAsync(
        @Qualifier("delimitedStepReaderBuffer")
        itemReader: ItemReader<Person>,
        @Qualifier("delimitedStepProcessorBuffer")
        itemProcessor: ItemProcessor<Person, PersonDTO>,
        @Qualifier("delimitedStepWriterBuffer")
        itemWriter: ItemWriter<PersonDTO>,
        @Qualifier("batchTransactionManager")
        transactionManager: PlatformTransactionManager,
        jobRepository: JobRepository,
        threadProperties: ThreadProperties,
        header: FileDelimitedFooter,
    ): Step {
        return StepBuilder("STEP_ASYNC_BUFFER", jobRepository)
            .chunk<Person, Future<PersonDTO>>(this.chuckDefault, transactionManager)
                .reader(itemReader)
                .processor(itemProcessorBufferAsync(itemProcessor, threadProperties))
                .writer(itemWriterBufferAsync(itemWriter))
                .listener(header)
                .build()
    }

    companion object {
        @JvmStatic
        private fun itemProcessorBufferAsync(
            itemProcessor: ItemProcessor<Person, PersonDTO>,
            threadProperties: ThreadProperties,
            namePrefix: String = "t-async-P-",
        ): ItemProcessor<Person, Future<PersonDTO>> {
            val asyncItemProcessor = AsyncItemProcessor<Person, PersonDTO>()
            val taskExecutor = BatchUtils.asyncExecutorProcessor(threadProperties, namePrefix)
            asyncItemProcessor.setTaskExecutor(taskExecutor)
            asyncItemProcessor.setDelegate(itemProcessor)
            return asyncItemProcessor;
        }

        @JvmStatic
        private fun itemWriterBufferAsync(
            itemWriter: ItemWriter<PersonDTO>,
        ): ItemWriter<Future<PersonDTO>> {
            val asyncItemWriter = AsyncItemWriter<PersonDTO>()
            asyncItemWriter.setDelegate(itemWriter)
            return asyncItemWriter
        }
    }
}