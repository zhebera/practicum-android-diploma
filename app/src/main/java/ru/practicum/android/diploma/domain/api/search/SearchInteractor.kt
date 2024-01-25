package ru.practicum.android.diploma.domain.api.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancies

interface SearchInteractor {
    fun searchMovies(vacancy: String): Flow<Pair<Vacancies?, String?>>
}
