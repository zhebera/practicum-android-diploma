package ru.practicum.android.diploma.domain.api.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.domain.models.VacancyDescription

interface SearchInteractor {
    fun searchVacancies(vacancy: String): Flow<Pair<Vacancies?, String?>>

    fun getVacancyDescription(vacancyId: String): Flow<Pair<VacancyDescription?, String?>>
}
