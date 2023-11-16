package org.project.async.buffer.core.common

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

interface FileInfo {
    val processDate: LocalDate
        get() = LocalDate.now()
    val fileEncoding: String
        get() = "CP037"
    val filePath: String
    val fileName: String
        get() = "default"
    val fileExtension: String
        get() = "txt"
}

