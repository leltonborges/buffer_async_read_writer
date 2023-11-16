package org.project.async.buffer.batch.async.db.fixed.step

import org.project.async.buffer.batch.ItemReaderPage
import org.project.async.buffer.core.model.buffer.Person
import org.project.async.buffer.core.pattern.dto.PersonDTO
import org.project.async.buffer.middleware.buffer.config.BufferConfigHandler
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@StepScope
@Component("fixedStepReader")
class FixedStepReader(
    private val bufferHandle: BufferConfigHandler
) : ItemReaderPage<Person>() {

    override fun startPage(): Pageable {
        return PageRequest.of(0, 1000)
    }

    override fun getData(pageable: Pageable): Page<Person> {
        return this.bufferHandle.readerData(arrayOf(0), pageable)
    }
}