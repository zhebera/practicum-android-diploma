package ru.practicum.android.diploma.data.response

import ru.practicum.android.diploma.data.dto.IndustriesDto
import ru.practicum.android.diploma.data.dto.Response

data class IndustriesResponse(val items: ArrayList<IndustriesDto>) : Response()
