package org.example.helloworld23.auth.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class LoginRequestDTO(
    @field:NotBlank
    @field:Email
    val email: String,

    @field:NotBlank
    val password: String
)
