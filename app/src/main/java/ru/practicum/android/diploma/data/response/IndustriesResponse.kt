package ru.practicum.android.diploma.data.response

import ru.practicum.android.diploma.data.dto.Response

data class IndustryResponse(
    val id: String,
    val name: String
)

data class IndustriesResponse(
    val industries: List<IndustryResponse>
) : Response()
