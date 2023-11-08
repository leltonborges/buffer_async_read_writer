package org.project.async.buffer.middleware.buffer.config

import org.project.async.buffer.core.model.buffer.Person
import org.project.async.buffer.core.pattern.dto.PersonDTO
import org.project.async.buffer.middleware.ProcessReaderPage
import org.project.async.buffer.middleware.buffer.FindPersonHandler
import org.project.async.buffer.service.PersonService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component("bufferConfigHandler")
class BufferConfigHandler(
    private val personService: PersonService
) : ProcessReaderPage<Person, PersonDTO, Array<Int>>() {

    override fun processHandler(page: Page<Person>): BufferHandler {
        val personHandler = FindPersonHandler()
        return BufferHandler.linkHandler(personHandler)
    }

    override fun primarySearch(item: Array<Int>, pageable: Pageable): Page<Person> {
        return this.personService.findByTypeDocument(item, pageable)
    }

}