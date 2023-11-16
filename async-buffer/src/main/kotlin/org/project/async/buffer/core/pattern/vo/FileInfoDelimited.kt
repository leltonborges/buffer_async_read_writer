package org.project.async.buffer.core.pattern.vo

import org.project.async.buffer.core.common.FileInfo

data class FileInfoDelimited(
    override val fileEncoding: String = "CP037",
    override val filePath: String,
    override val fileName: String = "default",
    override val fileExtension: String = "txt",
    val fileDelimiter: String = ";",
) : FileInfo