package org.example.helloworld23.core.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil {
    private val secretKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    private val expirationMs: Long = 3600000 * 24 // 24 hours

    fun generateToken(email: String): String {
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationMs))
            .signWith(secretKey)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            val claims = getClaims(token)
            !claims.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

    fun getEmailFromToken(token: String): String {
        return getClaims(token).subject
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
    }
}

@Component
class JwtAuthFilter(private val jwtUtil: JwtUtil) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7)
            if (jwtUtil.validateToken(token)) {
                val email = jwtUtil.getEmailFromToken(token)
                val auth = UsernamePasswordAuthenticationToken(
                    org.springframework.security.core.userdetails.User(
                        email,
                        "",
                        listOf()
                    ), null, listOf())
                SecurityContextHolder.getContext().authentication = auth
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token")
                return
            }
        }

        filterChain.doFilter(request, response)
    }
}

