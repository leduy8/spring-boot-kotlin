package org.example.helloworld23.user.controller

import jakarta.validation.Valid
import org.example.helloworld23.core.dto.PaginatedResponseDTO
import org.example.helloworld23.user.enums.UserSortField
import org.example.helloworld23.core.exception.InvalidSortFieldException
import org.example.helloworld23.user.dto.UserRequestCreateDTO
import org.example.helloworld23.user.dto.UserRequestUpdateDTO
import org.example.helloworld23.user.dto.UserResponseDTO
import org.example.helloworld23.user.service.UserService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
) {

    @GetMapping
    fun getUsers(
        @RequestParam(defaultValue = "\${app.default-page}") page: Int,
        @RequestParam(defaultValue = "\${app.default-size}") size: Int,
        @RequestParam(defaultValue = "id") orderBy: String // Example: "name" or "-name"
    ): ResponseEntity<PaginatedResponseDTO<UserResponseDTO>> {
        val isDesc = orderBy.startsWith("-")
        val direction = if (isDesc) Sort.Direction.DESC else Sort.Direction.ASC
        val orderField = if (isDesc) orderBy.substring(1) else orderBy // Remove "-" if present

        UserSortField.fromValue(orderField) ?: throw InvalidSortFieldException(orderField, UserSortField.allowedFields())

        val pageable = PageRequest.of(page - 1, size, Sort.by(direction, orderField))
        return ResponseEntity.ok(userService.getUsers(pageable))
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserResponseDTO> {
        val user = userService.getUserById(id)
        return if (user != null) ResponseEntity.ok(user) else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createUser(@Valid @RequestBody request: UserRequestCreateDTO): ResponseEntity<UserResponseDTO> {
        val newUser = userService.createUser(request)
        return ResponseEntity.ok(newUser)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @Valid @RequestBody updatedUser: UserRequestUpdateDTO): ResponseEntity<UserResponseDTO> {
        val user = userService.updateUser(id, updatedUser)
        return if (user != null) ResponseEntity.ok(user) else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }
}
