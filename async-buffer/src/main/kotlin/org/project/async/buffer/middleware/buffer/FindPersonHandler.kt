package org.project.async.buffer.middleware.buffer

import org.project.async.buffer.core.model.buffer.Person
import org.project.async.buffer.core.pattern.dto.LoginDTO
import org.project.async.buffer.core.pattern.dto.PersonDTO
import org.project.async.buffer.middleware.buffer.config.BufferHandler

class FindPersonHandler : BufferHandler() {

    override fun process(item: Person): PersonDTO {
        return PersonDTO(item)
    }

    override fun isInvalid(item: Person): Boolean {
        return item.id == null || item.id <= 0L
    }
}