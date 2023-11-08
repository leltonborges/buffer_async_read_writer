package org.project.async.buffer.core.enums

enum class TypeDocument(
    val code: Int,
) {
    TYPE_CPF(1), TYPE_CNPJ(2), TYPE_RG(3), TYPE_CRM(4);

    companion object {
        fun getByCode(expresion: (TypeDocument) -> Boolean): TypeDocument? {
            return TypeDocument
                    .values()
                    .firstOrNull { expresion(it) }
        }
    }
}