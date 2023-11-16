package org.project.async.buffer.batch.async.file.fixed.config

import org.project.async.buffer.batch.async.file.fixed.mapper.LineMapperFixed
import org.project.async.buffer.batch.utils.BatchUtils.mountPathFile
import org.project.async.buffer.core.enums.PersonFixed
import org.project.async.buffer.core.pattern.vo.PersonVO
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.transform.FixedLengthTokenizer
import org.springframework.batch.item.file.transform.LineTokenizer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Component


@JobScope
@Component
class FlatFileFixedDBConfig {
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
    @Bean("fixedStepReaderDB")
    fun itemReaderFlatFileFixed(): FlatFileItemReader<PersonVO> {
        val path = mountPathFile(pathRoot, path, fileName, fileExtension)
        val resource = FileSystemResource(path)
        return FlatFileItemReaderBuilder<PersonVO>()
            .name("itemReaderFlatFileFixed")
            .resource(resource)
            .fixedLength()
            .columns(*PersonFixed.fieldRanges())
            .names(*PersonFixed.fieldNames())
            .encoding(encoding)
            .targetType(PersonVO::class.java)
            .lineMapper(LineMapperFixed(lineMapper()))
            .build();
    }

    private fun lineMapper(): DefaultLineMapper<PersonVO> {
        val lineMapper = DefaultLineMapper<PersonVO>()
        lineMapper.setLineTokenizer(lineTokenizer())
        lineMapper.setFieldSetMapper(fieldSetMapper())
        return lineMapper;
    }

    private fun fieldSetMapper(): BeanWrapperFieldSetMapper<PersonVO> {
        val setMapper = BeanWrapperFieldSetMapper<PersonVO>()
        setMapper.setTargetType(PersonVO::class.java)
        return setMapper;
    }

    private fun lineTokenizer(): LineTokenizer {
        val tokenizer = FixedLengthTokenizer()
        tokenizer.setNames(*PersonFixed.fieldNames())
        tokenizer.setColumns(*PersonFixed.fieldRanges())
        return tokenizer
    }
}