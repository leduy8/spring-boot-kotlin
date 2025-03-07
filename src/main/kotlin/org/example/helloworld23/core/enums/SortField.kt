package org.example.helloworld23.core.enums

interface BaseSortField {
    val fieldName: String

    companion object {
        fun <T> fromValue(value: String, values: Array<T>): T? where T : Enum<T>, T : BaseSortField {
            return values.firstOrNull { it.fieldName == value }
        }

        fun <T> allowedFields(values: Array<T>): List<String> where T : Enum<T>, T : BaseSortField {
            return values.map { it.fieldName }
        }
    }
}