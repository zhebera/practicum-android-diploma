package ru.practicum.android.diploma.data.dto

data class ContactsResponse(
    val email: String? = null,
    val name: String? = null,
    val phones: List<PhoneResponse>? = emptyList()
)
