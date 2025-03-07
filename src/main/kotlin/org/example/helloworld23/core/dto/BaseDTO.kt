package org.example.helloworld23.core.dto

abstract class BaseDTO<T> {
    companion object {
        inline fun <reified D : BaseDTO<T>, T> of(data: T, mapper: (T) -> D): D {
            return mapper(data)
        }
    }
}