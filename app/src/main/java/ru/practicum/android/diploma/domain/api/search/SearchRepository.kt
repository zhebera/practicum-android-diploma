package ru.practicum.android.diploma.domain.api.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

interface SearchRepository {

    fun searchVacancies(vacancy: String): Flow<Resource<Vacancies>>

    fun add(newIt: Vacancy)
}
