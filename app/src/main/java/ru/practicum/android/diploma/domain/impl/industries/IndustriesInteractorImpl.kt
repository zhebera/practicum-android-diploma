package ru.practicum.android.diploma.domain.impl.industries

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.api.industries.IndustriesInteractor
import ru.practicum.android.diploma.domain.api.industries.IndustriesRepository
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

class IndustriesInteractorImpl(private val industriesRepository: IndustriesRepository) : IndustriesInteractor {
    override fun getIndustries(): Flow<Pair<List<Industry>?, String?>> {
        return industriesRepository.getIndustries().map { result ->
            when (result) {
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
