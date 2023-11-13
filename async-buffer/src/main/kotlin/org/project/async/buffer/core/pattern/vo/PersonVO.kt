package org.project.async.buffer.core.pattern.vo

data class PersonVO(
    val name: String,
    val document: String,
    val typeDocument: Int,
    val age: Int,
    val login: String,
    val password: String,
    val dtLasUpdatePass: String? = null,
    val dtLastAcess: String? = null,
    val dtCreatedAt: String? = null,
)