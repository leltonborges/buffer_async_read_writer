package org.project.async.buffer.batch.async.file.delimeted.mapper

import org.project.async.buffer.core.common.Constants
import org.project.async.buffer.core.pattern.dto.PersonDTO
import org.springframework.batch.item.file.LineMapper

class LineMapperDelimited(
    private val lineMapper: LineMapper<PersonDTO>
) : LineMapper<PersonDTO?> {
    private val header = Constants.PERSON_NAMES_FILE.joinToString(separator = ";")

    override fun mapLine(line: String, lineNumber: Int): PersonDTO {
        if (invalidLine(line))
            return PersonDTO()

        return lineMapper.mapLine(line, lineNumber)
    }

    private fun invalidLine(line: String): Boolean {
        return line.trim().startsWith(header, true)
                || line.trim().startsWith("Total de", true)
    }
}