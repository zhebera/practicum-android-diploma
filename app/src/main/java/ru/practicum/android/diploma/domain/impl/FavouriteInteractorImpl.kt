package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.favourite.FavouriteInteractor
import ru.practicum.android.diploma.domain.api.favourite.FavouriteRepository
import ru.practicum.android.diploma.domain.models.VacancyDescription

class FavouriteInteractorImpl(private val favouriteRepository: FavouriteRepository): FavouriteInteractor {
    override fun getAllVacancies(): Flow<List<VacancyDescription>> {
        return favouriteRepository.getAllVacancies()
    }

    override suspend fun addVacancy(vacancy: VacancyDescription) {
        favouriteRepository.addVacancy(vacancy)
    }

    override suspend fun removeVacancy(vacancy: VacancyDescription) {
        favouriteRepository.removeVacancy(vacancy)
    }
}
