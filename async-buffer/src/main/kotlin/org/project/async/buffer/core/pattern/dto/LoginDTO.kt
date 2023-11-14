package org.project.async.buffer.core.pattern.dto

import org.project.async.buffer.core.model.buffer.Login
import org.project.async.buffer.core.utils.DateUtils.convertString

class LoginDTO(
        var login: String = "",
        var password: String = "",
        var dtLastUpdatePass: String = "",
        var dtLastAcess: String = "",
        var dtCreatedAt: String = ""
) {
    constructor(login: Login?)
            : this(login?.login ?: "",
                   login?.password ?: "",
                   convertString(login?.dtLastUpdatePass),
                   convertString(login?.dtLastAcess),
                   convertString(login?.dtCreatedAt))

}
