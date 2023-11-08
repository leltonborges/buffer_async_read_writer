package org.project.async.buffer.config.job

import org.project.async.buffer.core.common.Constants
import org.project.async.buffer.core.pattern.dto.PersonDTO
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor
import org.springframework.batch.item.file.transform.FieldExtractor
import org.springframework.batch.item.file.transform.FormatterLineAggregator
import org.springframework.batch.item.file.transform.LineAggregator
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Configuration
class JobConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    fun taskExecutor(): ThreadPoolTaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 5
        executor.maxPoolSize = 10
        executor.queueCapacity = 25
        executor.setThreadNamePrefix("async-")
        executor.initialize()
        return executor
    }

    @Bean("lineAggregatorFixed")
    fun lineAggregatorFixed(
        @Qualifier("fieldExtractorPersonFixed") fieldExtractor: FieldExtractor<PersonDTO>,
    ): LineAggregator<PersonDTO> {
        val lineAggregator = FormatterLineAggregator<PersonDTO>().apply {
            setFieldExtractor(fieldExtractor)
            setFormat(Constants.FORMAT_FIELD_FIXED_FILE)
        }
        return lineAggregator;
    }

    @Bean("fieldExtractorPersonFixed")
    fun fieldExtractorPerson(): FieldExtractor<PersonDTO> {
        val fieldExtractor = BeanWrapperFieldExtractor<PersonDTO>()
        fieldExtractor.setNames(Constants.PERSON_NAMES_FILE)
        return fieldExtractor
    }
}