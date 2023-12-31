package org.project.async.buffer.batch.async.db.fixed.config

import org.project.async.buffer.batch.utils.BatchUtils
import org.project.async.buffer.core.pattern.vo.PersonVO
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.file.FlatFileFooterCallback
import org.springframework.batch.item.file.FlatFileHeaderCallback
import org.springframework.batch.item.file.FlatFileItemWriter
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder
import org.springframework.batch.item.file.transform.LineAggregator
import org.springframework.batch.item.support.SynchronizedItemStreamWriter
import org.springframework.batch.item.support.builder.SynchronizedItemStreamWriterBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Component

@JobScope
@Component
class FlatFileFixedConfig {

    @Value("#{jobParameters['fileEncoding']}")
    private lateinit var encoding: String

    @Value("#{jobParameters['filePath']}")
    private lateinit var path: String

    @Value("#{jobParameters['fileName']}")
    private lateinit var fileName: String

    @Value("#{jobParameters['fileExtension']}")
    private lateinit var fileExtension: String

    @Value("\${buffer.root.path}")
    private lateinit var pathRoot: String

    @StepScope
    @Bean("flatFileWriterPersonFixed")
    fun flatFileWriterPerson(
        @Qualifier("fileFixedHeader") headerCallback: FlatFileHeaderCallback,
        @Qualifier("fileFixedFooter") footerCallback: FlatFileFooterCallback,
        @Qualifier("lineAggregatorFixed") lineAggregator: LineAggregator<PersonVO>
    ): FlatFileItemWriter<PersonVO> {
        val path = BatchUtils.mountPathFile(pathRoot, path, fileName, fileExtension, true)
        val resource = FileSystemResource(path)
        return FlatFileItemWriterBuilder<PersonVO>()
                .name("FILE_WRITER_PERSON")
                .resource(resource)
                .headerCallback(headerCallback)
                .footerCallback(footerCallback)
                .encoding(encoding)
                .lineAggregator(lineAggregator)
                .build()
    }

    @StepScope
    @Bean("asyncItemStreamWriterFixed")
    fun asyncItemStreamWriter(
        @Qualifier("flatFileWriterPersonFixed") flatFileItemWriter: FlatFileItemWriter<PersonVO>,
    ): SynchronizedItemStreamWriter<PersonVO> {
        return SynchronizedItemStreamWriterBuilder<PersonVO>().delegate(flatFileItemWriter).build();
    }
}