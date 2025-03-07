package org.example.helloworld23.auth.service

import org.example.helloworld23.auth.dto.LoginRequestDTO
import org.example.helloworld23.core.utils.JwtUtil
import org.example.helloworld23.user.repository.UserRepository
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) {
    fun login(request: LoginRequestDTO): String {
        val user = userRepository.findByEmail(request.email)
            ?: throw BadCredentialsException("Invalid email or password")

        if (!passwordEncoder.matches(request.password, user.passwordHash)) {
            throw BadCredentialsException("Invalid email or password")
        }

        return jwtUtil.generateToken(user.email)
    }
}
