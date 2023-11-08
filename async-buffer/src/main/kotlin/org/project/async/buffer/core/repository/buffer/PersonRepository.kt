package org.project.async.buffer.core.repository.buffer

import org.project.async.buffer.core.model.buffer.Person
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : JpaRepository<Person, Long> {

    @Query(value = "select p from Person p              " +
            " left join Login l on l.id = p.id          " +
            " where p.typeDocument in :types            ")
    fun findByTypeDocument(@Param("types") typeDocument: Set<Int>, pageable: Pageable): Page<Person>;
}