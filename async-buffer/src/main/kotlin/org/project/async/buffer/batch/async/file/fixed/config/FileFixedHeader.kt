package org.project.async.buffer.batch.async.file.fixed.config

import org.project.async.buffer.core.common.Constants.PERSON_NAMES_FILE
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.item.file.FlatFileHeaderCallback
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.Writer

@Component
@JobScope
class FileFixedHeader(
    @Value("#{jobParameters['fileDelimiter']}") private val delimiter: String,
) : FlatFileHeaderCallback {
    override fun writeHeader(writer: Writer) {
        writer.append(PERSON_NAMES_FILE.joinToString(separator = delimiter))
    }
}