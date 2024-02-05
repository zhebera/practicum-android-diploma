package ru.practicum.android.diploma.domain.api.countries

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Country

interface CountriesRepository {
    fun getCountries(): Flow<List<Country>>
}
