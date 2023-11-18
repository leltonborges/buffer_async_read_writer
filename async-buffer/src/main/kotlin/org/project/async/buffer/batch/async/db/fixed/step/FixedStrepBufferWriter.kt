package org.project.async.buffer.batch.async.db.fixed.step

import jakarta.annotation.PreDestroy
import org.project.async.buffer.batch.utils.BatchUtils
import org.project.async.buffer.core.pattern.vo.PersonVO
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.file.transform.LineAggregator
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.Charset

@Component
@StepScope
class FixedStrepBufferWriter(
    @Qualifier("lineAggregatorFixed") private val lineAggregator: LineAggregator<PersonVO>
) : ItemWriter<PersonVO> {
    private lateinit var bufferedOutputStream: BufferedOutputStream

    @Value("#{jobParameters['fileEncoding']}")
    private lateinit var encoding: String

    @Value("#{jobParameters['filePath']}")
    private lateinit var path: String

    @Value("#{jobParameters['fileName']}")
    private lateinit var fileName: String

    @Value("#{jobParameters['fileExtension'] ?: ''}")
    private lateinit var fileExtension: String

    @Value("\${buffer.root.path}")
    private lateinit var pathRoot: String

    private val charset: Charset = Charset.forName(encoding)
    private val separatorLine: String = String(byteArrayOf(0x15), charset)

    override fun write(chunk: Chunk<out PersonVO>) {
        if (!::bufferedOutputStream.isInitialized) {
            initializeBufferedOutputStream()
        }

        chunk.map { formatData(it) }.forEach { writeString(it) }
    }

    private fun formatData(it: PersonVO) = lineAggregator.aggregate(it)


    private fun initializeBufferedOutputStream() {
        val pathDirectory = BatchUtils.mountDirectoryFile(pathRoot, path)
        val directory = File(pathDirectory)
        directory.mkdirs()

        val path = BatchUtils.mountPathFile(pathRoot, path, fileName + "_buffer", fileExtension, true)
        bufferedOutputStream = BufferedOutputStream(FileOutputStream(path))
    }

    private fun writeString(data: String) {
        val bytes = data.toByteArray(charset)
        bufferedOutputStream.use { it.write(bytes) }
    }

    @PreDestroy
    fun close() {
        if (::bufferedOutputStream.isInitialized) {
            bufferedOutputStream.close()
        }
    }
}
