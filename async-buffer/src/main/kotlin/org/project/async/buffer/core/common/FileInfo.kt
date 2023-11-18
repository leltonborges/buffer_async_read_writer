package org.project.async.buffer.core.common

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
        get() = ""

    val separatorLine: String
        get() = "0x0D"
}

