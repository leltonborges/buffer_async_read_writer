package org.project.async.buffer.core.repository.buffer

import org.project.async.buffer.core.model.buffer.Login
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LoginRepository : JpaRepository<Login, Long> {}