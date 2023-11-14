package org.project.async.buffer.batch.async.db.delimeted.config

import org.project.async.buffer.core.common.Constants
import org.project.async.buffer.core.pattern.dto.PersonDTO
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Component
import java.io.File
import java.util.regex.Matcher


@JobScope
@Component
class FlatFileDelimitedResourceConfig {
    @Value("#{jobParameters['fileEncoding']}")
    private lateinit var encoding: String

    @Value("#{jobParameters['filePath']}")
    private lateinit var path: String

    @Value("#{jobParameters['fileDelimiter']}")
    private lateinit var delimiter: String

    @Value("\${buffer.root.path}")
    private lateinit var pathRoot: String

    @Bean
    @StepScope
    fun itemReaderFlatFileDelimited(): FlatFileItemReader<PersonDTO> {
        val path = "$pathRoot/$path".replace(Regex("[/\\\\]"), Matcher.quoteReplacement(File.separator))
        val resource = FileSystemResource(path)
        return FlatFileItemReaderBuilder<PersonDTO>()
                .name("itemReaderFlatFileDelimited")
                .resource(resource)
                .delimited()
                .delimiter(delimiter)
                .names(*Constants.PERSON_NAMES_FILE)
                .encoding(encoding)
                .targetType(PersonDTO::class.java)
                .build();
    }

}