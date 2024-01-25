package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDescription

interface SearchInteractor {

    fun getVacancyDescription(vacancyId: String): Flow<Pair<VacancyDescription?, String?>>
}
