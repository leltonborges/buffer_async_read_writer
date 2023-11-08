package org.project.async.buffer.core.model.buffer

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "TB_USER_LOGIN", schema = "buffer")
class Login(
        @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
        @field:Column(name = "CD_SEQ_PERSON")
        val id: Long,

        @field:Column(name = "VL_LOGIN", nullable = false)
        val login: String,

        @field:Column(name = "VL_PASSWORD", nullable = false)
        val password: String,

        @field:Column(name = "DT_LAST_UPDATE_PASS", nullable = false)
        val dtLasUpdatePass: LocalDateTime,

        @field:Column(name = "DT_LAST_ACCESS", nullable = false)
        val dtLastAcess: LocalDateTime,

        @field:Column(name = "DT_CREATED_AT", nullable = false)
        val dtCreatedAt: LocalDateTime
)