package ru.practicum.android.diploma.domain.api.industries

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Industry

interface IndustriesRepository {
    fun getIndustries(): Flow<List<Industry>>
}
