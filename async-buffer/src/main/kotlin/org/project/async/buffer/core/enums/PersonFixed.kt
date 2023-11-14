package org.project.async.buffer.core.enums

import org.project.async.buffer.functional.size
import org.springframework.batch.item.file.transform.Range
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

enum class PersonFixed(val field: String, val format: String, val range: Range) {
    NAME("name", "%-10s", Range(1, 10)),
    DOCUMENT("document", "%-10s", Range(11, 20)),
    DOCUMENT_TYPE("typeDocument", "%1d", Range(21, 21)),
    AGE("age", "%2d", Range(22, 23)),
    LOGIN("login", "%-17s", Range(24, 40)),
    PASSWORD("password", "%-110s", Range(41, 150)),
    DT_LAST_UPDATE("dtLasUpdatePass", "%8s", Range(151, 158)),
    DT_LAST_ACESS("dtLastAcess", "%8s", Range(159, 166)),
    DT_CREATED_AT("dtCreatedAt", "%8s", Range(167, 174));


    fun processField(value: String): String {
        return value.substring(0, minOf(value.length, this.range.size()))
    }

    fun processField(value: Int): Int {
        return if (value.toString().length <= this.range.size()) value
        else throw NumberFormatException("Numero numero extenso: $value")
    }

    fun processField(value: LocalDateTime, format: DateTimeFormatter): String {
        val formattedDate = value.format(format)
        return processField(formattedDate)
    }

    companion object {
        @JvmStatic
        fun fieldNames(): Array<String> {
            return PersonFixed.values().map { it.field }.toTypedArray();
        }

        @JvmStatic
        fun fieldFormatted(): String {
            return values().joinToString(separator = "") { it.format }
        }
    }
}
