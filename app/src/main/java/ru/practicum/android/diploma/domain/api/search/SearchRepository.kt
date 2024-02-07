package ru.practicum.android.diploma.domain.api.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.domain.models.VacancyDescription
import ru.practicum.android.diploma.util.Resource

interface SearchRepository {

    fun searchVacancies(options: HashMap<String, String>): Flow<Resource<Vacancies>>

    fun getVacancyDescription(vacancyId: String): Flow<Resource<VacancyDescription>>
}
