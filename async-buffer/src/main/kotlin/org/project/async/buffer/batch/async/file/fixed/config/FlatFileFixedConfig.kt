package org.project.async.buffer.batch.async.file.fixed.config

import org.project.async.buffer.core.common.Constants
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
import org.springframework.batch.item.file.transform.FormatterLineAggregator
import org.springframework.batch.item.file.transform.LineAggregator
import org.springframework.batch.item.support.SynchronizedItemStreamWriter
import org.springframework.batch.item.support.builder.SynchronizedItemStreamWriterBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource

@Configuration
@JobScope
class FlatFileFixedConfig {

    @Value("#{jobParameters['fileEncoding']}")
    private lateinit var encoding: String

    @Value("#{jobParameters['fileDelimiter']}")
    private lateinit var delimiter: String

    @Value("#{jobParameters['filePath']}")
    private lateinit var path: String

    @Value("\${buffer.root.path}")
    private lateinit var pathRoot: String

    @StepScope
    @Bean("flatFileWriterPersonFixed")
    fun flatFileWriterPerson(
        @Qualifier("fieldExtractorPersonFixed") fieldExtractor: FieldExtractor<PersonDTO>,
        @Qualifier("fileFixedHeader") headerCallback: FlatFileHeaderCallback,
        @Qualifier("fileFixedFooter") footerCallback: FlatFileFooterCallback,
        @Qualifier("lineAggregatorFixed") lineAggregator: LineAggregator<PersonDTO>
    ): FlatFileItemWriter<PersonDTO> {
        val timeMillis = System.currentTimeMillis()
        val path = "$pathRoot/$path/write-fixed-$timeMillis.txt".replace(Regex("/{2,}"), "/")
        val resource = FileSystemResource(path)
        return FlatFileItemWriterBuilder<PersonDTO>()
                .name("FILE_WRITER_PERSON")
                .resource(resource)
                .headerCallback(headerCallback)
                .footerCallback(footerCallback)
                .encoding(encoding)
                .lineAggregator(lineAggregator)
                .build()
    }

    @StepScope
    @Bean("syncItemStreamWriterFixed")
    fun syncItemStreamWriter(
        @Qualifier("flatFileWriterPersonFixed") flatFileItemWriter: FlatFileItemWriter<PersonDTO>,
    ): SynchronizedItemStreamWriter<PersonDTO> {
        return SynchronizedItemStreamWriterBuilder<PersonDTO>().delegate(flatFileItemWriter).build();
    }
}