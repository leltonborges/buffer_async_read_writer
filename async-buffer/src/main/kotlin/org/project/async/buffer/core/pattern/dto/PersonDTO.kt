package org.project.async.buffer.core.pattern.dto

import org.project.async.buffer.core.model.buffer.Person

class PersonDTO(
        val name: String = "",
        val document: String = "",
        val typeDocument: Int = 0,
        val age: Int = 0,
        val login: LoginDTO = LoginDTO()
) {
    constructor(person: Person)
            : this(person.name,
            person.document,
            person.typeDocument,
            person.age,
            LoginDTO(person.login))
}
