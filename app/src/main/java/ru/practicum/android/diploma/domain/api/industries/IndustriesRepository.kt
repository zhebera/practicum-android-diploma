package ru.practicum.android.diploma.domain.api.industries

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

interface IndustriesRepository {
    fun getIndustries(): Flow<Resource<List<Industry>>>
}
