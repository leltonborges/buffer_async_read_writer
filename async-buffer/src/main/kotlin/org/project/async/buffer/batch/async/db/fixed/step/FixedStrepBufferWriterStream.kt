package org.project.async.buffer.batch.async.db.fixed.step

import jakarta.annotation.PreDestroy
import org.project.async.buffer.config.job.FileInfoProperties
import org.project.async.buffer.core.pattern.vo.PersonVO
import org.project.async.buffer.functional.logInfoWriter
import org.project.async.buffer.functional.logger
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ExecutionContext
import org.springframework.batch.item.ItemStream
import org.springframework.batch.item.ItemStreamWriter
import org.springframework.batch.item.file.transform.LineAggregator
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.Charset

@StepScope
@Component("fixedStrepBufferWriterStream")
class FixedStrepBufferWriterStream(
    @Qualifier("lineAggregatorFixed") private val lineAggregator: LineAggregator<PersonVO>,
    @Qualifier("fileInfoProperties") private val fileProperties: FileInfoProperties
) : ItemStreamWriter<PersonVO>, ItemStream {
    private lateinit var bufferedOutputStream: BufferedOutputStream

    private val charset: Charset = Charset.forName(fileProperties.encoding)

    private fun formatData(personVO: PersonVO) = lineAggregator.aggregate(personVO) + fileProperties.separatorLine

    private fun initializeBufferedOutputStream() {
        val pathDirectory = fileProperties.fullDirection
        val directory = File(pathDirectory)
        if (!directory.exists()) directory.mkdirs()
        val path = fileProperties.fullPathWithName("_buffer", true)

        bufferedOutputStream = BufferedOutputStream(FileOutputStream(path))
    }

    private fun writeString(data: String) {
        val bytes = data.toByteArray(charset)
        bufferedOutputStream.write(bytes)
    }

    override fun write(chunk: Chunk<out PersonVO>) {
        val stopWatch = StopWatch()
        stopWatch.start()
        chunk.map { formatData(it) }.forEach { writeString(it) }
        stopWatch.stop()
        logInfoWriter(stopWatch, chunk.size(), this.logger("bufferWriterStream"))
    }

    override fun open(executionContext: ExecutionContext) {
        initializeBufferedOutputStream()
    }

    @PreDestroy
    override fun close() {
        if (::bufferedOutputStream.isInitialized) {
            bufferedOutputStream.close()
        }
    }
}
