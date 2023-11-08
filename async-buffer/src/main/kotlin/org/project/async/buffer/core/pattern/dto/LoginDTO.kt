package org.project.async.buffer.core.pattern.dto

import org.project.async.buffer.core.model.buffer.Login
import java.time.LocalDateTime

data class LoginDTO(
        val login: String = "",
        val password: String = "",
        val dtLasUpdatePass: LocalDateTime? = null,
        val dtLastAcess: LocalDateTime? = null,
        val dtCreatedAt: LocalDateTime? = null
) {
    constructor(login: Login?)
            : this(login?.login ?: "",
                   login?.password ?: "",
                   login?.dtLasUpdatePass,
                   login?.dtLastAcess,
                   login?.dtCreatedAt)
}
