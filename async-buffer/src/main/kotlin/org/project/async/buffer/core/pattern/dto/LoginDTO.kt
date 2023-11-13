package org.project.async.buffer.core.pattern.dto

import org.project.async.buffer.core.model.buffer.Login
import java.time.LocalDateTime

class LoginDTO(
    var login: String = "",
    var password: String = "",
    var dtLastUpdatePass: LocalDateTime? = null,
    var dtLastAcess: LocalDateTime? = null,
    var dtCreatedAt: LocalDateTime? = null
) {
    constructor(login: Login?)
            : this(login?.login ?: "",
                   login?.password ?: "",
                   login?.dtLastUpdatePass,
                   login?.dtLastAcess,
                   login?.dtCreatedAt)
}
