package org.project.async.buffer.core.model.buffer

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.project.async.buffer.core.pattern.dto.LoginDTO
import org.project.async.buffer.core.utils.DateUtils.convertDate
import java.time.LocalDateTime

@Entity
@Table(name = "TB_USER_LOGIN", schema = "buffer")
class Login(
        @field:Id
        @field:Column(name = "CD_SEQ_PERSON")
        var id: Long?,

        @field:Column(name = "VL_LOGIN", nullable = false)
        val login: String,

        @field:Column(name = "VL_PASSWORD", nullable = false)
        val password: String,

        @field:Column(name = "DT_LAST_UPDATE_PASS")
        val dtLastUpdatePass: LocalDateTime?,

        @field:Column(name = "DT_LAST_ACCESS")
        val dtLastAcess: LocalDateTime?,

        @field:Column(name = "DT_CREATED_AT")
        val dtCreatedAt: LocalDateTime?,
) {
    constructor(dto: LoginDTO) : this(null,
                                      dto.login,
                                      dto.password,
                                      convertDate(dto.dtLastUpdatePass),
                                      convertDate(dto.dtLastAcess),
                                      convertDate(dto.dtCreatedAt))
}