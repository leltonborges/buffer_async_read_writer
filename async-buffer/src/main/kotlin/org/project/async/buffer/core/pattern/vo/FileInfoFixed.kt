package org.project.async.buffer.core.pattern.vo

import org.project.async.buffer.core.common.FileInfo

data class FileInfoFixed(
    override val fileEncoding: String = "CP037",
    override val filePath: String,
    override val fileName: String = "default",
    override val fileExtension: String = "",
    override val separatorLine: String = "0x25"
) : FileInfo
