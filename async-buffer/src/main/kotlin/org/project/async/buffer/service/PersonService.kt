package org.project.async.buffer.service

import org.project.async.buffer.core.enums.TypeDocument
import org.project.async.buffer.core.model.buffer.Person
import org.project.async.buffer.core.repository.buffer.PersonRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PersonService(
        private val personRepository: PersonRepository,
        private val longService: LoginService
) {
    fun findByTypeDocument(typesDocument: Array<Int>, pageable: Pageable): Page<Person> {
        val types: Set<Int> = if (typesDocument.contains(0)) {
            TypeDocument
                    .values()
                    .map { it.code }
                    .toSet()
        } else {
            typesDocument.toSet()
        }
        return personRepository.findByTypeDocument(types, pageable)
    }

    @Transactional(transactionManager = "bufferTransactionManager")
    fun saveAllAndFlush(entities: List<Person>) {
        entities.forEach { person ->
            val login = person.login
            person.login = null
            val persist = this.personRepository.saveAndFlush(person)
            login?.let {
                it.id = persist.id
                this.longService.save(it)
            }
         }
    }
}