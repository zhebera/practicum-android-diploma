package ru.practicum.android.diploma.domain.impl.countries

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.api.countries.CountriesInteractor
import ru.practicum.android.diploma.domain.api.countries.CountriesRepository
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.util.Resource

class CountriesInteractorImpl(private val countriesRepository: CountriesRepository) : CountriesInteractor {
    override fun getCountries(): Flow<Pair<List<Country>?, String?>> {
        return countriesRepository.getCountries().map { result ->
            when(result){
                is Resource.Success -> {
                    Pair(result.data, null)
                }
                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }
}
