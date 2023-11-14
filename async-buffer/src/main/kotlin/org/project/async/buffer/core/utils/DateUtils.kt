package org.project.async.buffer.core.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateUtils {
    @JvmStatic
    val formatted: DateTimeFormatter
        get() = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")

    @JvmStatic
    fun convertString(value: LocalDateTime?): String {
        return value?.format(formatted) ?: "";
    }

    @JvmStatic
    fun convertDate(value: String?): LocalDateTime? {
        return LocalDateTime.parse(value, formatted)
    }
}