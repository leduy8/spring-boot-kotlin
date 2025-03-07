package org.example.helloworld23.user.service

import org.example.helloworld23.core.dto.PaginatedResponseDTO
import org.example.helloworld23.user.dto.UserRequestCreateDTO
import org.example.helloworld23.user.dto.UserRequestUpdateDTO
import org.example.helloworld23.user.dto.UserResponseDTO
import org.example.helloworld23.user.model.User
import org.example.helloworld23.user.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun getUsers(pageable: Pageable): PaginatedResponseDTO<UserResponseDTO> {
        val page: Page<User> = userRepository.findAll(pageable)
        return PaginatedResponseDTO.of(page, UserResponseDTO::of)
    }

    fun getUserById(id: Long): UserResponseDTO? {
        val user = userRepository.findById(id).orElse(null)
        return UserResponseDTO.of(user)
    }

    fun createUser(request: UserRequestCreateDTO): UserResponseDTO {
        val tempUser = User(
            name = request.name,
            email = request.email,
            passwordHash = passwordEncoder.encode(request.password)
        )
        val newUser = userRepository.save(tempUser)
        return UserResponseDTO.of(newUser)
    }

    fun updateUser(id: Long, updatedUser: UserRequestUpdateDTO): UserResponseDTO? {
        var existingUser = userRepository.findById(id).orElse(null) ?: return null
        existingUser.apply {
            name = updatedUser.name
            email = updatedUser.email
        }
        existingUser = userRepository.save(existingUser)
        return UserResponseDTO.of(existingUser)
    }

    fun deleteUser(id: Long) = userRepository.deleteById(id)
}
