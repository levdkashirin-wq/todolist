package com.example.todoapi.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                ErrorResponse(
                    message = e.message ?: "Resource not found",
                    statusCode = HttpStatus.NOT_FOUND.value(),
                    timestamp = LocalDateTime.now()
                )
            )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse(
                    message = e.message ?: "Bad request",
                    statusCode = HttpStatus.BAD_REQUEST.value(),
                    timestamp = LocalDateTime.now()
                )
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericError(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponse(
                    message = "Internal server error",
                    statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    timestamp = LocalDateTime.now()
                )
            )
    }
}

data class ErrorResponse(
    val message: String,
    val statusCode: Int,
    val timestamp: LocalDateTime
)