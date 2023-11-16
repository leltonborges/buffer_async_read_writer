package org.project.async.buffer.core.pattern.vo

data class PersonVO(
    var name: String = "",
    var document: String = "",
    var typeDocument: Int = 0,
    var age: Int = 0,
    var login: String = "",
    var password: String = "",
    var dtLasUpdatePass: String? = null,
    var dtLastAcess: String? = null,
    var dtCreatedAt: String? = null,
)