package org.project.async.buffer.batch.async.db.delimeted.step

import org.project.async.buffer.batch.AbstractItemWriterStep
import org.project.async.buffer.core.model.buffer.Person
import org.project.async.buffer.functional.logger
import org.project.async.buffer.functional.logInfoWriter
import org.project.async.buffer.service.PersonService
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.Chunk
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch

@StepScope
@Component("itemWriterFlatFileDelimited")
class DelimitedStepWriter(
    private val personService: PersonService
): AbstractItemWriterStep<Person>() {

    override fun write(chunk: Chunk<out Person>) {
        val stopWatch = StopWatch()
        stopWatch.start()
        this.personService.saveAllAndFlush(chunk.items)
        stopWatch.stop()
        logInfoWriter(stopWatch, chunk.size(), this.logger("itemWriterFlatFileDelimited"))
    }
}