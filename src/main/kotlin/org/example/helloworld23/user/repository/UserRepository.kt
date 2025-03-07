package org.example.helloworld23.user.repository

import org.example.helloworld23.user.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    override fun findAll(pageable: Pageable): Page<User>

    fun findByEmail(email: String): User?
}
