package ru.practicum.android.diploma.data.industries

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.industries.IndustriesRepository
import ru.practicum.android.diploma.domain.models.Industry

class IndustriesRepositoryImpl : IndustriesRepository {
    override fun getIndustries(): Flow<List<Industry>> {
        TODO("Not yet implemented")
    }
}
