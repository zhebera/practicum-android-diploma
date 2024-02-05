package ru.practicum.android.diploma.data.countries

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.countries.CountriesRepository
import ru.practicum.android.diploma.domain.models.Country

class CountriesRepositoryImpl : CountriesRepository {
    override fun getCountries(): Flow<List<Country>> {
        TODO("Not yet implemented")
    }
}
