package org.example.helloworld23.core.dto

import java.time.LocalDateTime

data class ErrorResponseDTO(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val message: String,
    val errors: Map<String, String>? = null
)
