package ru.practicum.android.diploma.data.response

import ru.practicum.android.diploma.data.dto.Response

data class CountriesResponse(
    val countries: List<CountryResponse>
) : Response()

data class CountryResponse(
    val id: String,
    val name: String,
    val url: String
)
