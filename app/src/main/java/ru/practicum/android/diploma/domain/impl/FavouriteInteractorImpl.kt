package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.api.favourite.FavouriteInteractor
import ru.practicum.android.diploma.domain.api.favourite.FavouriteRepository
import ru.practicum.android.diploma.domain.models.VacancyDescription
import ru.practicum.android.diploma.util.Resource

class FavouriteInteractorImpl(private val favouriteRepository: FavouriteRepository) : FavouriteInteractor {
    override fun getAllVacancies(): Flow<List<VacancyDescription>> {
        return favouriteRepository.getAllVacancies()
    }

    override suspend fun addVacancy(vacancy: VacancyDescription) {
        favouriteRepository.addVacancy(vacancy)
    }

    override suspend fun removeVacancy(vacancy: VacancyDescription) {
        favouriteRepository.removeVacancy(vacancy)
    }

    override fun checkVacancy(id: String): Flow<Boolean> {
        return favouriteRepository.checkVacancy(id)
    }

    override suspend fun getVacancyDescriptionById(vacancyId: String): Flow<Pair<VacancyDescription?, String?>> {
        return favouriteRepository.getVacancyDescriptionById(vacancyId).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }
}
