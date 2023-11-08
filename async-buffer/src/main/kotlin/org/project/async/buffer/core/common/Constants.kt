package org.project.async.buffer.core.common

object Constants {
    @JvmStatic
    val PROCESS_DATE: String = "processDate"

    @JvmStatic
    val PERSON_NAMES_FILE = arrayOf("name",
                                    "document",
                                    "typeDocument",
                                    "age",
                                    "login.login",
                                    "login.password",
                                    "login.dtLasUpdatePass",
                                    "login.dtLastAcess",
                                    "login.dtCreatedAt")

    @JvmStatic
    val FORMAT_FIELD_FIXED_FILE = "%-10s%-4s%-2d%-10s%-10s%-10s%-10s%-10s"
}