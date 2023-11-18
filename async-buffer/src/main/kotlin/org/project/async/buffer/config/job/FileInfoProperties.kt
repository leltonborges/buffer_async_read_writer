package org.project.async.buffer.config.job

import org.project.async.buffer.core.pattern.vo.PersonVO
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.file.transform.LineAggregator
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File
import java.nio.charset.Charset
import java.util.regex.Matcher

@Component
@StepScope
data class FileInfoProperties(
    @Qualifier("lineAggregatorFixed")
    val lineAggregator: LineAggregator<PersonVO>,
    @Value("#{jobParameters['fileEncoding']}")
    val encoding: String,
    @Value("#{jobParameters['filePath']}")
    val path: String,
    @Value("#{jobParameters['fileName']}")
    val fileName: String,
    @Value("#{jobParameters['fileExtension']}")
    val fileExtension: String,
    @Value("#{jobParameters['fileDelimiter'] ?: ''}")
    val fileDelimiter: String,
    @Value("\${buffer.root.path}")
    val pathRoot: String,
    @Value("#{jobParameters['separatorLine']}")
    private val _separatorLine: String
) {
    private val pattern = Regex("[/\\\\]")
    private val matcher = Matcher.quoteReplacement(File.separator);

    val separatorLine: String
        get() = separatorLineValue()

    val fullDirection: String
        get() = "$pathRoot/$path".replace(pattern, matcher)


    val fullPathWithName: (suffix: String, times: Boolean) -> String
        get() = { suffix: String, times: Boolean ->
            val extension = if (fileExtension.startsWith(".")) fileExtension else fileExtension.padStart(0, '.')
            val suffixName = suffix.takeIf { it.isNotBlank() } ?: ""
            val timesPath = if (times) "_${System.currentTimeMillis()}" else ""
            "$pathRoot/$path/$fileName$suffixName$timesPath$extension".replace(pattern, matcher)
        }

    private fun separatorLineValue(): String {
        val byteEncoding = byteEncoding()
        return byteEncoding?.let { String(byteArrayOf(it), Charset.forName(encoding)) } ?: ""
    }

    private fun byteEncoding(): Byte? {
        return if (_separatorLine.isNotBlank() && _separatorLine.startsWith("0x")) {
            _separatorLine.removePrefix("0x").toInt(16).toByte()
        } else null
    }
}
