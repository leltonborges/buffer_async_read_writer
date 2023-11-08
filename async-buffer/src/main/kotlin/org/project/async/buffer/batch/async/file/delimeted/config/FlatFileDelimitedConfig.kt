package org.project.async.buffer.batch.async.file.delimeted.config

import org.project.async.buffer.core.common.Constants.PERSON_NAMES_FILE
import org.project.async.buffer.core.pattern.dto.PersonDTO
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.file.FlatFileFooterCallback
import org.springframework.batch.item.file.FlatFileHeaderCallback
import org.springframework.batch.item.file.FlatFileItemWriter
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor
import org.springframework.batch.item.file.transform.FieldExtractor
import org.springframework.batch.item.support.SynchronizedItemStreamWriter
import org.springframework.batch.item.support.builder.SynchronizedItemStreamWriterBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource

@Configuration
class FlatFileDelimitedConfig {

    @Bean("flatFileWriterPersonDelimited")
    @StepScope
    fun flatFileWriterPerson(
        @Qualifier("fieldExtractorPersonDelimited") fieldExtractor: FieldExtractor<PersonDTO>,
        @Qualifier("fileDelimitedHeader") headerCallback: FlatFileHeaderCallback,
        @Qualifier("fileDelimitedFooter") footerCallback: FlatFileFooterCallback,
        @Value("#{jobParameters['fileEncoding']}") encoding: String,
        @Value("#{jobParameters['fileDelimiter']}") delimiter: String,
        @Value("#{jobParameters['filePath']}") path: String,
        @Value("\${buffer.root.path}") pathRoot: String,
    ): FlatFileItemWriter<PersonDTO> {
        val pattern = Regex("/{2,}")
        val fullPathFile = "${pathRoot}/$path/write-delimited-${System.currentTimeMillis()}.txt".replace(pattern, "/")
        val resource = FileSystemResource(fullPathFile)
        return FlatFileItemWriterBuilder<PersonDTO>()
                .name("FILE_WRITER_PERSON")
                .resource(resource)
                .headerCallback(headerCallback)
                .footerCallback(footerCallback)
                .encoding(encoding)
                .delimited()
                .delimiter(delimiter)
                .fieldExtractor(fieldExtractor)
//                .names(*NAMES_FILE_DEMILITED)
                .build()
    }

    @Bean("fieldExtractorPersonDelimited")
    @StepScope
    fun fieldExtractorPerson(): FieldExtractor<PersonDTO> {
        val fieldExtractor = BeanWrapperFieldExtractor<PersonDTO>()
        fieldExtractor.setNames(PERSON_NAMES_FILE)
        return fieldExtractor
    }

    @Bean("syncItemStreamWriterDelimited") @StepScope fun syncItemStreamWriter(
        @Qualifier("flatFileWriterPersonDelimited") flatFileItemWriter: FlatFileItemWriter<PersonDTO>,
    ): SynchronizedItemStreamWriter<PersonDTO> {
        return SynchronizedItemStreamWriterBuilder<PersonDTO>().delegate(flatFileItemWriter).build();
    }
}