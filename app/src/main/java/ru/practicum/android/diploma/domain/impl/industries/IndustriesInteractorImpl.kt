package ru.practicum.android.diploma.domain.impl.industries

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.industries.IndustriesInteractor
import ru.practicum.android.diploma.domain.api.industries.IndustriesRepository
import ru.practicum.android.diploma.domain.models.Industry

class IndustriesInteractorImpl(private val industriesRepository: IndustriesRepository) : IndustriesInteractor {
    override fun getIndustries(): Flow<List<Industry>> {
        return industriesRepository.getIndustries()
    }
}
