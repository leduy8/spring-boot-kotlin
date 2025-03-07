package org.example.helloworld23.core.exception

import org.example.helloworld23.core.dto.ErrorResponseDTO
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {

    // Handles validation errors when throw InvalidSortFieldException
    @ExceptionHandler(InvalidSortFieldException::class)
    fun handleInvalidSortField(ex: InvalidSortFieldException): ResponseEntity<Map<String, Any>> {
        val errorResponse = mapOf(
            "error" to ex.message!!,
        )
        return ResponseEntity.badRequest().body(errorResponse)
    }

    // Handles validation errors when @Valid fails inside @RequestBody
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponseDTO> {
        val errors = ex.bindingResult.fieldErrors.associate { it.field to it.defaultMessage.orEmpty() }
        val errorResponse = ErrorResponseDTO(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            message = "Validation failed",
            errors = errors
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    // Handles validation errors from @RequestParam or @PathVariable
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(ex: ConstraintViolationException): ResponseEntity<ErrorResponseDTO> {
        val errors = ex.constraintViolations.associate { it.propertyPath.toString() to it.message }
        val errorResponse = ErrorResponseDTO(
            status = HttpStatus.BAD_REQUEST.value(),
            message = "Validation failed",
            errors = errors
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    // Handles any unexpected exceptions
    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<ErrorResponseDTO> {
        val errorResponse = ErrorResponseDTO(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = ex.message ?: "An unexpected error occurred"
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
