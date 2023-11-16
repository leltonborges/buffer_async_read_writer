package org.project.async.buffer.batch.async.db.delimeted.config

import org.project.async.buffer.core.pattern.dto.PersonDTO
import org.springframework.batch.core.annotation.AfterWrite
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.file.FlatFileFooterCallback
import org.springframework.stereotype.Component
import java.io.Writer

@Component
@JobScope
class FileDelimitedFooter : FlatFileFooterCallback {
    private var counter = 0

    override fun writeFooter(writer: Writer) {
        writer.append("Total de $counter registros")
    }

    @AfterWrite
    fun afterWrite(chunk: Chunk<out PersonDTO>) {
        this.counter += chunk.size()
    }
}