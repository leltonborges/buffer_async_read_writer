package org.project.async.buffer.batch.async.db.fixed.config

import org.project.async.buffer.core.enums.PersonFixed
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.file.FlatFileHeaderCallback
import org.springframework.stereotype.Component
import java.io.Writer

@Component
@StepScope
class FileFixedHeader() : FlatFileHeaderCallback {
    private val header = PersonFixed.joinValues("");

    override fun writeHeader(writer: Writer) {
        writer.append(header)
    }
}