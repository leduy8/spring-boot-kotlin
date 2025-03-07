package org.example.helloworld23.user.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Column(nullable = false)
    var name: String,

    @field:NotBlank(message = "Email cannot be blank")
    @field:Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    var email: String,

    @field:NotBlank(message = "Password cannot be blank")
    @field:Size(max = 255, message = "Password cannot exceed 255 characters")
    @Column(nullable = false)
    var passwordHash: String
) {
    constructor() : this(0, "", "", "") // No-arg constructor for JPA
}
