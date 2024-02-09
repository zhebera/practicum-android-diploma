package ru.practicum.android.diploma.domain.api.favourite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDescription
import ru.practicum.android.diploma.util.Resource

interface FavouriteRepository {

    fun getAllVacancies(): Flow<List<VacancyDescription>>
    suspend fun addVacancy(vacancy: VacancyDescription)
    suspend fun removeVacancy(vacancy: VacancyDescription)
    fun checkVacancy(id: String): Flow<Boolean>
    suspend fun getVacancyDescriptionById(id: String): Flow<Resource<VacancyDescription?>>
}
