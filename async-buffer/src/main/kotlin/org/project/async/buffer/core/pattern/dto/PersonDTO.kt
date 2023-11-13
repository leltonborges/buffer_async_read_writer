package org.project.async.buffer.core.pattern.dto

import org.project.async.buffer.core.model.buffer.Person

class PersonDTO(
        var name: String = "",
        var document: String = "",
        var typeDocument: Int = 0,
        var age: Int = 0,
        var login: LoginDTO = LoginDTO()
) {
    constructor(person: Person)
            : this(person.name,
            person.document,
            person.typeDocument,
            person.age,
            LoginDTO(person.login))
}
