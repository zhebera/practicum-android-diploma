package ru.practicum.android.diploma.ui.search.viewmodel

import ru.practicum.android.diploma.domain.models.Vacancies

sealed interface SearchState {

    object Default : SearchState

    object Loading : SearchState

    object Empty : SearchState

    data class Content(
        val data: Vacancies,
        val currentPage: Int?
    ) : SearchState

    data class Error(
        val message: String
    ) : SearchState
}
