package ru.practicum.android.diploma.domain.api.countries

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Country

interface CountriesInteractor {

    fun getCountries(): Flow<Pair<List<Country>?, String?>>
}
