package org.project.async.buffer.batch.async.file.fixed.mapper

import org.project.async.buffer.core.enums.PersonFixed
import org.project.async.buffer.core.pattern.dto.PersonDTO
import org.project.async.buffer.core.pattern.vo.PersonVO
import org.springframework.batch.item.file.LineMapper

class LineMapperFixed(
    private val lineMapper: LineMapper<PersonVO>
) : LineMapper<PersonVO?> {
    private val header = PersonFixed.joinValues("");

    override fun mapLine(line: String, lineNumber: Int): PersonVO {
        if (invalidLine(line))
            return PersonVO()

        return lineMapper.mapLine(line, lineNumber)
    }

    private fun invalidLine(line: String): Boolean {
        return line.trim().startsWith(header, true)
                || line.trim().startsWith("Total de", true)
    }
}