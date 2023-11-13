package org.project.async.buffer.batch.async.file.fixed.config

import org.project.async.buffer.core.enums.PersonFixed
import org.project.async.buffer.functional.size
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.file.FlatFileHeaderCallback
import org.springframework.stereotype.Component
import java.io.Writer

@Component
@StepScope
class FileFixedHeader() : FlatFileHeaderCallback {
    private val personFixed = PersonFixed.values();

    override fun writeHeader(writer: Writer) {
        val header = personFixed.joinToString(separator = "") { formatFieldForHeader(it) }
        writer.append(header)
    }

    private fun formatFieldForHeader(field: PersonFixed): String {
        val formattedField = String.format(field.format.replace("d", "s"), field.field)
        return if (formattedField.length > field.range.size()) {
            formattedField.substring(0, field.range.size())
        } else {
            formattedField.padEnd(field.range.size())
        }
    }
}