package org.project.async.buffer.middleware.buffer.config

import org.project.async.buffer.core.model.buffer.Person
import org.project.async.buffer.core.pattern.dto.PersonDTO
import org.project.async.buffer.middleware.AbstractHandler

abstract class BufferHandler : AbstractHandler<Person, PersonDTO>() {
    override fun defaultReturnProcess(): PersonDTO {
        return PersonDTO()
    }

    companion object {
        @JvmStatic
        fun linkHandler(vararg bufferHandler: BufferHandler): BufferHandler {
            return link(*bufferHandler)
        }
    }
}