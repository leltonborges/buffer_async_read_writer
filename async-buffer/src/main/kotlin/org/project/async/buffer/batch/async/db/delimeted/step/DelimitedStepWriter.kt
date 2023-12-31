package org.project.async.buffer.batch.async.db.delimeted.step

import jakarta.annotation.PostConstruct
import org.project.async.buffer.batch.AbstractItemWriterStep
import org.project.async.buffer.core.pattern.dto.PersonDTO
import org.project.async.buffer.functional.logger
import org.project.async.buffer.functional.logInfoWriter
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ExecutionContext
import org.springframework.batch.item.support.SynchronizedItemStreamWriter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch

@StepScope
@Component("delimitedStepWriterBuffer")
class DelimitedStepWriter(
    @Qualifier("syncItemStreamWriterDelimited") private val itemStreamWriter: SynchronizedItemStreamWriter<PersonDTO>,
): AbstractItemWriterStep<PersonDTO>() {

    @PostConstruct
    fun init(){
        this.itemStreamWriter.open(ExecutionContext())
    }

    override fun write(chunk: Chunk<out PersonDTO>) {
        val stopWatch = StopWatch()
        stopWatch.start()
        this.itemStreamWriter.write(chunk)
        stopWatch.stop()
        logInfoWriter(stopWatch, chunk.size(), this.logger())
    }
}