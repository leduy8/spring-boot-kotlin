package org.example.helloworld23.core.exception

class InvalidSortFieldException(field: String, allowedFields: List<String>)
    : RuntimeException("Invalid sort field: $field. Allowed fields: [${allowedFields.joinToString(", ")}]")

