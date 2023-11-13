package org.project.async.buffer.core.handler

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
class ExceptionHandlerAdvise : ResponseEntityExceptionHandler() {

    @ExceptionHandler(NoSuchElementException::class)
    fun objectNotFound(ex: NoSuchElementException, request: HttpServletRequest): ResponseEntity<StandardError> {
        val notFound = HttpStatus.NOT_FOUND
        val error = StandardError(System.currentTimeMillis(),
                                  notFound.value(),
                                  "Not Found",
                                  ex.message ?: "",
                                  request.requestURI)
        return ResponseEntity
                .status(notFound)
                .body(error)
    }
}