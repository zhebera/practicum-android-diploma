package ru.practicum.android.diploma.domain.api.industries

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

interface IndustriesInteractor {
    fun getIndustries(): Flow<Pair<List<Industry>?, String?>>
}
