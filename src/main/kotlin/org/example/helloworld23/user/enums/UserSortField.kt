package org.example.helloworld23.user.enums

import org.example.helloworld23.core.enums.BaseSortField

enum class UserSortField(override val fieldName: String) : BaseSortField {
    ID("id"),
    NAME("name"),
    EMAIL("email");

    companion object {
        fun fromValue(value: String): UserSortField? {
            return BaseSortField.fromValue(value, enumValues<UserSortField>())
        }

        fun allowedFields(): List<String> {
            return BaseSortField.allowedFields(enumValues<UserSortField>())
        }
    }
}
