package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.IndustriesDto
import ru.practicum.android.diploma.domain.models.Industry

object IndustriesConverter {

    fun map(industries: IndustriesDto): Industry {
        return Industry(
            id = industries.id!!,
            name = industries.name!!
        )
    }

    fun map(industry: Industry): IndustriesDto {
        return IndustriesDto(
            id = industry.id,
            name = industry.name,
            industries = null
        )
    }
}
