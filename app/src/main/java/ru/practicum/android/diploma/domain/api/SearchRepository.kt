package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDescription
import ru.practicum.android.diploma.util.Resource

interface SearchRepository {

    fun getVacancyDescription(vacancyId: String): Flow<Resource<VacancyDescription>>
}
