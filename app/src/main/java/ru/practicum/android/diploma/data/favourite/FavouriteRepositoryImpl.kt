package ru.practicum.android.diploma.data.favourite

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.converters.db.VacancyDbConverter
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.domain.api.favourite.FavouriteRepository
import ru.practicum.android.diploma.domain.models.VacancyDescription
import ru.practicum.android.diploma.util.Resource

class FavouriteRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val vacancyDbConverter: VacancyDbConverter,
    context: Context
) : FavouriteRepository {

    private val badConnection by lazy {
        context.getString(R.string.bad_connection)
    }

    override fun getAllVacancies(): Flow<List<VacancyDescription>> = flow {
        val vacancies = appDatabase.vacancyDao().getAllVacancies()
        emit(vacancyDbConverter.convert(vacancies))
    }

    override suspend fun addVacancy(vacancy: VacancyDescription) {
        appDatabase.vacancyDao().addVacancy(vacancyDbConverter.convertToEntity(vacancy))
    }

    override suspend fun removeVacancy(vacancy: VacancyDescription) {
        val vacanciesId = appDatabase.vacancyDao().getAllVacancies().map { it.id }
        if (vacanciesId.contains(vacancy.id)) {
            appDatabase.vacancyDao().removeVacancy(vacancy.id)
        }
    }

    override fun checkVacancy(vacancyId: String) = flow {
        val answer = appDatabase.vacancyDao().getVacancyById(vacancyId) != null
        emit(answer)
    }

    override suspend fun getVacancyDescriptionById(id: String): Flow<Resource<VacancyDescription?>> = flow {
        val vacancyDescription = appDatabase.vacancyDao().getVacancyById(id)
        if (vacancyDescription != null) {
            emit(Resource.Success(VacancyDbConverter().convertFromEntity(vacancyDescription)))
        } else {
            emit(Resource.Error(badConnection))
        }
    }
}
