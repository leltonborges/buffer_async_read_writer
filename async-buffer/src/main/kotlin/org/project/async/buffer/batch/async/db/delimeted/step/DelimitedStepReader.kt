package org.project.async.buffer.batch.async.db.delimeted.step

import org.project.async.buffer.batch.ItemReaderPage
import org.project.async.buffer.core.model.buffer.Person
import org.project.async.buffer.service.PersonService
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@StepScope
@Component("delimitedStepReaderBuffer")
class DelimitedStepReader(
    private val personService: PersonService
) : ItemReaderPage<Person>() {

    override fun startPage(): Pageable {
        return PageRequest.of(0, 1000)
    }

    override fun getData(pageable: Pageable): Page<Person> {
        return this.personService.findByTypeDocument(arrayOf(0), pageable)
    }
}