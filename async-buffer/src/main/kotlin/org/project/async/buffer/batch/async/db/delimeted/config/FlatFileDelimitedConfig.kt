package org.project.async.buffer.batch.async.db.delimeted.config

import org.project.async.buffer.batch.utils.BatchUtils
import org.project.async.buffer.core.common.Constants.PERSON_NAMES_FILE
import org.project.async.buffer.core.pattern.dto.PersonDTO
import org.springframework.batch.core.configuration.annotation.JobScope
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
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Component

@Component
@JobScope
class FlatFileDelimitedConfig {

    @Value("#{jobParameters['fileEncoding']}")
    private lateinit var encoding: String

    @Value("#{jobParameters['filePath']}")
    private lateinit var path: String

    @Value("#{jobParameters['fileName']}")
    private lateinit var fileName: String

    @Value("#{jobParameters['fileExtension']}")
    private lateinit var fileExtension: String

    @Value("#{jobParameters['fileDelimiter']}")
    private lateinit var fileDelimiter: String

    @Value("\${buffer.root.path}")
    private lateinit var pathRoot: String

    @Bean("flatFileWriterPersonDelimited")
    @StepScope
    fun flatFileWriterPerson(
        @Qualifier("fieldExtractorPersonDelimited") fieldExtractor: FieldExtractor<PersonDTO>,
        @Qualifier("fileDelimitedHeader") headerCallback: FlatFileHeaderCallback,
        @Qualifier("fileDelimitedFooter") footerCallback: FlatFileFooterCallback
    ): FlatFileItemWriter<PersonDTO> {
        val fullPathFile = BatchUtils.mountPathFile(pathRoot, path, fileName, fileExtension, true)
        val resource = FileSystemResource(fullPathFile)
        return FlatFileItemWriterBuilder<PersonDTO>()
            .name("FILE_WRITER_PERSON")
            .resource(resource)
            .headerCallback(headerCallback)
            .footerCallback(footerCallback)
            .encoding(encoding)
            .delimited()
            .delimiter(fileDelimiter)
            .fieldExtractor(fieldExtractor)
            .build()
    }

    @Bean("fieldExtractorPersonDelimited")
    @StepScope
    fun fieldExtractorPerson(): FieldExtractor<PersonDTO> {
        val fieldExtractor = BeanWrapperFieldExtractor<PersonDTO>()
        fieldExtractor.setNames(PERSON_NAMES_FILE)
        return fieldExtractor
    }

    @Bean("syncItemStreamWriterDelimited")
    @StepScope
    fun syncItemStreamWriter(
        @Qualifier("flatFileWriterPersonDelimited") flatFileItemWriter: FlatFileItemWriter<PersonDTO>,
    ): SynchronizedItemStreamWriter<PersonDTO> {
        return SynchronizedItemStreamWriterBuilder<PersonDTO>().delegate(flatFileItemWriter).build();
    }
}