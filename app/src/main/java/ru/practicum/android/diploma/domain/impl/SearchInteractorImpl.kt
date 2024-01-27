package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.api.SearchInteractor
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.models.VacancyDescription
import ru.practicum.android.diploma.util.Resource

class SearchInteractorImpl(
    private val repository: SearchRepository
) : SearchInteractor {
    override fun getVacancyDescription(vacancyId: String): Flow<Pair<VacancyDescription?, String?>> {
        return repository.getVacancyDescription(vacancyId).map { result ->
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
