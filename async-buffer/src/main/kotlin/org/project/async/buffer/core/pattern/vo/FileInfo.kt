package org.project.async.buffer.core.pattern.vo

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class FileInfo(
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    val processDate: LocalDate = LocalDate.now(),
    val fileEncoding: String = "CP037",
    val filePath: String,
    val fileDelimiter: String = ";",
)
