package org.project.async.buffer.core.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateUtils {
    @JvmStatic
    val formattedProcess: DateTimeFormatter
        get() = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
    @JvmStatic
    val formattedProcessFixed: DateTimeFormatter
        get() = DateTimeFormatter.ofPattern("yyyyMMdd")
    @JvmStatic
    val formattedToString: DateTimeFormatter
        get() = DateTimeFormatter.ofPattern("yyyyMMdd")

    @JvmStatic
    fun convertString(value: LocalDateTime?): String {
        return value?.format(formattedProcess) ?: "";
    }

    @JvmStatic
    fun convertDate(value: String?): LocalDateTime? {
        return LocalDateTime.parse(value, formattedProcess)
    }

    @JvmStatic
    fun convertDateFixed(value: String): LocalDateTime {
        val localDate = LocalDate.parse(value, formattedProcessFixed)
        return localDate.atStartOfDay()
    }
}