package org.example.helloworld23.core.dto

import org.springframework.data.domain.Page

data class PaginatedResponseDTO<T>(
    val data: List<T>,
    val currentPage: Int,
    val totalPages: Int,
    val totalItems: Long,
    val pageSize: Int,
    val isLastPage: Boolean
) {
    companion object {
        fun <E, D : BaseDTO<E>> of(
            page: Page<E>,
            mapper: (E) -> D
        ): PaginatedResponseDTO<D> {
            return PaginatedResponseDTO(
                data = page.content.map(mapper),
                currentPage = page.number + 1, // Spring Page is 0-based, so we adjust
                totalPages = page.totalPages,
                totalItems = page.totalElements,
                pageSize = page.size,
                isLastPage = page.isLast
            )
        }
    }
}
