package org.project.async.buffer.batch.async.file.delimeted.config

import org.project.async.buffer.batch.async.file.delimeted.mapper.LineMapperDelimited
import org.project.async.buffer.batch.utils.BatchUtils
import org.project.async.buffer.core.common.Constants
import org.project.async.buffer.core.pattern.dto.PersonDTO
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.batch.item.file.transform.LineTokenizer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Component

@JobScope
@Component
class FlatFileDelimitedResourceConfig {
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


    @StepScope
    @Bean("stepAsyncDelimitedReaderDB")
    fun stepAsyncDelimitedReaderDB(): FlatFileItemReader<PersonDTO> {
        val fullPathFile = BatchUtils.mountPathFile(pathRoot, path, fileName, fileExtension)
        val resource = FileSystemResource(fullPathFile)
        return FlatFileItemReaderBuilder<PersonDTO>()
            .name("itemReaderFlatFileDelimited")
            .resource(resource)
            .delimited()
            .delimiter(fileDelimiter)
            .names(*Constants.PERSON_NAMES_FILE)
            .encoding(encoding)
            .targetType(PersonDTO::class.java)
            .lineMapper(LineMapperDelimited(lineMapper()))
            .build();
    }

    private fun lineMapper(): DefaultLineMapper<PersonDTO> {
        val lineMapper = DefaultLineMapper<PersonDTO>()
        lineMapper.setLineTokenizer(lineTokenizer())
        lineMapper.setFieldSetMapper(fieldSetMapper())
        return lineMapper;
    }

    private fun fieldSetMapper(): BeanWrapperFieldSetMapper<PersonDTO> {
        val setMapper = BeanWrapperFieldSetMapper<PersonDTO>()
        setMapper.setTargetType(PersonDTO::class.java)
        return setMapper;
    }

    private fun lineTokenizer(): LineTokenizer {
        val tokenizer = DelimitedLineTokenizer()
        tokenizer.setDelimiter(fileDelimiter)
        tokenizer.setNames(*Constants.PERSON_NAMES_FILE)
        return tokenizer
    }
}