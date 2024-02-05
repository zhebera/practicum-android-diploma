package ru.practicum.android.diploma.domain.impl.countries

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.countries.CountriesInteractor
import ru.practicum.android.diploma.domain.api.countries.CountriesRepository
import ru.practicum.android.diploma.domain.models.Country

class CountriesInteractorImpl(private val countriesRepository: CountriesRepository) : CountriesInteractor {
    override fun getCountries(): Flow<List<Country>> {
        return countriesRepository.getCountries()
    }
}
