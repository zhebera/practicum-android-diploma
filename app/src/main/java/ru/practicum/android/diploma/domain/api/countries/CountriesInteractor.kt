package ru.practicum.android.diploma.domain.api.countries

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.util.Resource

interface CountriesInteractor {

    fun getCountries(): Flow<Pair<List<Country>?, String?>>
}
