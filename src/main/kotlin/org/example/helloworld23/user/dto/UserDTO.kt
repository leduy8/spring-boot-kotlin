package org.example.helloworld23.user.dto

import org.example.helloworld23.user.model.User
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.example.helloworld23.core.dto.BaseDTO

data class UserResponseDTO(
    val id: Long,
    val name: String,
    val email: String
) : BaseDTO<User>() {
    companion object {
        fun of(user: User): UserResponseDTO {
            return of(user) { u ->
                UserResponseDTO(
                    u.id,
                    u.name,
                    u.email
                )
            }
        }
    }
}

data class UserRequestCreateDTO(
    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    val name: String,

    @field:NotBlank(message = "Email cannot be blank")
    @field:Email(message = "Invalid email format")
    val email: String,

    @field:NotBlank
    @field:Size(min = 4, message = "Password must be at least 4 characters long")
    val password: String
)

data class UserRequestUpdateDTO(
    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    val name: String,

    @field:NotBlank(message = "Email cannot be blank")
    @field:Email(message = "Invalid email format")
    val email: String,
)
