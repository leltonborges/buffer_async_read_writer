package org.project.async.buffer.core.model.buffer

import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "TB_PERSON", schema = "buffer")
class Person(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Column(name = "SEQ_PERSON")
    val id: Long,

    @field:Column(name = "VL_NAME", nullable = false)
    val name: String,

    @field:Column(name = "VL_DOCUMENT", nullable = false)
    val document: String,

    @field:Column(name = "CD_TYPE_DOCUMENT", nullable = false)
    val typeDocument: Int,

    @field:Column(name = "CD_AGE", nullable = false)
    val age: Int,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SEQ_PERSON", referencedColumnName = "CD_SEQ_PERSON")
    val login: Login
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Person) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}