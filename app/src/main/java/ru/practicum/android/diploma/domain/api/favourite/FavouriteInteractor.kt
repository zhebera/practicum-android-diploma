package ru.practicum.android.diploma.domain.api.favourite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDescription

interface FavouriteInteractor {

    fun getAllVacancies(): Flow<List<VacancyDescription>>
    fun addVacancy(vacancy: VacancyDescription)
    fun removeVacancy(vacancy: VacancyDescription)
}
