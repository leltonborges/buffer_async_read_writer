package org.project.async.buffer.batch.async.db.delimeted.step

import jakarta.annotation.PostConstruct
import org.project.async.buffer.batch.AbstractItemWriterStep
import org.project.async.buffer.core.model.buffer.Person
import org.project.async.buffer.core.pattern.dto.PersonDTO
import org.project.async.buffer.functional.logger
import org.project.async.buffer.functional.loggerWriter
import org.project.async.buffer.service.PersonService
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ExecutionContext
import org.springframework.batch.item.support.SynchronizedItemStreamWriter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch
import java.util.Collections
import java.util.concurrent.Future
import java.util.stream.Collectors

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
        loggerWriter(stopWatch, chunk.size(), this.logger())
    }
}