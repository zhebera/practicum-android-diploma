package ru.practicum.android.diploma.data.favourite

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converters.VacancyDbConverter
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.domain.api.favourite.FavouriteRepository
import ru.practicum.android.diploma.domain.models.VacancyDescription

class FavouriteRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val vacancyDbConverter: VacancyDbConverter
) : FavouriteRepository {
    override fun getAllVacancies(): Flow<List<VacancyDescription>> = flow {
        val vacancies = appDatabase.vacancyDao().getAllVacancies()
        emit(vacancyDbConverter.convert(vacancies))
    }

    override suspend fun addVacancy(vacancy: VacancyDescription) {
        appDatabase.vacancyDao().addVacancy(vacancyDbConverter.convert(vacancy))
    }

    override suspend fun removeVacancy(vacancy: VacancyDescription) {
        val vacanciesId = appDatabase.vacancyDao().getAllVacancies().map { it.id }
        if (vacanciesId.contains(vacancy.id))
            appDatabase.vacancyDao().removeVacancy(vacancy.id)
    }
}
