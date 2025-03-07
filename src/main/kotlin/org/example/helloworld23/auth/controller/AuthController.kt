package org.example.helloworld23.auth.controller

import jakarta.validation.Valid
import org.example.helloworld23.auth.dto.LoginRequestDTO
import org.example.helloworld23.auth.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequestDTO): ResponseEntity<Map<String, String>> {
        val token = authService.login(request)
        return ResponseEntity.ok(mapOf("accessToken" to token))
    }
}
