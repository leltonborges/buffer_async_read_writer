package org.project.async.buffer.core.handler

data class StandardError(
    val timestamp: Long,
    val status: Int,
    val error: String,
    val message: String,
    val path: String,
)

