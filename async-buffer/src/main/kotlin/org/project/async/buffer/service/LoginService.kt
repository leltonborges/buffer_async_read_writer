package org.project.async.buffer.service

import org.project.async.buffer.core.model.buffer.Login
import org.project.async.buffer.core.repository.buffer.LoginRepository
import org.springframework.stereotype.Service

@Service
class LoginService(
        private val loginRepository: LoginRepository
) {
    fun save(login: Login) {
        this.loginRepository.save(login)
    }
}