package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.response.IndustriesResponse
import ru.practicum.android.diploma.data.response.IndustryResponse
import ru.practicum.android.diploma.domain.models.Industry

object IndustriesConverter {

    fun map(industryResponse: IndustryResponse): Industry {
        return Industry(
            industryResponse.id,
            industryResponse.name
        )
    }

    fun map(industriesResponse: IndustriesResponse): List<Industry> {
        return industriesResponse.industries.map {
            map(it)
        }
    }
}
