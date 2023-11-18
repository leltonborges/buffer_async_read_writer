package org.project.async.buffer.batch.async.file.fixed.step

import jakarta.annotation.PreDestroy
import org.project.async.buffer.config.job.FileInfoProperties
import org.project.async.buffer.core.pattern.vo.PersonVO
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ExecutionContext
import org.springframework.batch.item.ItemStream
import org.springframework.batch.item.ItemStreamReader
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.nio.charset.Charset

@StepScope
@Component("fixedStrepBufferReaderStream")
class FixedStrepBufferReaderStream(
    @Qualifier("fileInfoProperties") private val fileProperties: FileInfoProperties
) : ItemStreamReader<PersonVO>, ItemStream {
    private lateinit var bufferedInputStream: BufferedInputStream

    private val charset: Charset = Charset.forName(fileProperties.encoding)

    private fun initializeBufferedOutputStream() {
        val pathDirectory = fileProperties.fullDirection
        val directory = File(pathDirectory)
        if (!directory.exists()) directory.mkdirs()
        val path = fileProperties.fullPathWithName("", false)

        bufferedInputStream = BufferedInputStream(FileInputStream(path))
    }

    override fun open(executionContext: ExecutionContext) {
        initializeBufferedOutputStream()
    }

    @PreDestroy
    override fun close() {
        if (::bufferedInputStream.isInitialized) {
            bufferedInputStream.close()
        }
    }

    override fun read(): PersonVO? {
        val buffer = ByteArray(174)
        val byteRead = this.bufferedInputStream.read(buffer)
        if (byteRead == -1) return null

        val data = String(buffer, 0, byteRead, charset)
        println("Person: $data")
        return PersonVO()
    }
}
