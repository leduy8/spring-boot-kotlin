package org.example.helloworld23.user.service

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.example.helloworld23.user.dto.UserRequestCreateDTO
import org.example.helloworld23.user.model.User
import org.example.helloworld23.user.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import kotlin.test.assertEquals

@SpringBootTest
@ExtendWith(MockKExtension::class)
class UserServiceTest {

    @MockK
    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var passwordEncoder: PasswordEncoder

    private lateinit var userService: UserService

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        userService = UserService(userRepository, passwordEncoder)
    }

    @Test
    fun `createUser should save user with encoded password`() {
        val rawPassword = "password123"
        val encodedPassword = "encoded-password"
        val user = User(id = 1L, name = "John", email = "john@example.com", passwordHash = encodedPassword)

        every { passwordEncoder.encode(rawPassword) } returns encodedPassword
        every { userRepository.save(any()) } returns user

        val result = userService.createUser(UserRequestCreateDTO(
            name = "John",
            email = "john@example.com",
            password = rawPassword
        ))

        assertEquals("John", result.name)
        assertEquals("john@example.com", result.email)

        verify { userRepository.save(any()) }
    }
}
