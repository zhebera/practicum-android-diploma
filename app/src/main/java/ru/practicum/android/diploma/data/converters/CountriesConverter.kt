package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.response.CountriesResponse
import ru.practicum.android.diploma.data.response.CountryResponse
import ru.practicum.android.diploma.domain.models.Country

object CountriesConverter {

    fun map(countriesResponse: CountriesResponse): List<Country> {
        return countriesResponse.countries.map {
            map(it)
        }
    }

    fun map(countryResponse: CountryResponse): Country {
        return Country(
            countryResponse.id,
            countryResponse.name,
            countryResponse.url
        )
    }
}
