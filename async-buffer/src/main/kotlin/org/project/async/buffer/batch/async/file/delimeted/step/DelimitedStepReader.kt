package org.project.async.buffer.batch.async.file.delimeted.step

import org.project.async.buffer.batch.ItemReaderPage
import org.project.async.buffer.core.pattern.dto.PersonDTO
import org.project.async.buffer.middleware.buffer.config.BufferConfigHandler
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@StepScope
@Component("delimitedStepReaderBuffer")
class DelimitedStepReader(
    private val bufferHandle: BufferConfigHandler
) : ItemReaderPage<PersonDTO>() {

    override fun startPage(): Pageable {
        return PageRequest.of(0, 1000)
    }

    override fun getData(pageable: Pageable): Page<PersonDTO> {
        return this.bufferHandle.readerData(arrayOf(0), pageable)
    }
}