package ru.practicum.android.diploma.data.response

import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.domain.models.Industry


data class IndustryResponse(
    val id: String,
    val name: String,
    val industries: List<Industry>
)

data class IndustriesResponse(
    val industries: List<IndustryResponse>
) : Response()

