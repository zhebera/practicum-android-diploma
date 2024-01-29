package ru.practicum.android.diploma.ui.search.viewmodel

import ru.practicum.android.diploma.domain.models.Vacancies

sealed interface SearchState {

    object Loading : SearchState

    object Empty : SearchState

    data class Content(
        val data: Vacancies
    ) : SearchState

    data class Error(
        val message: String
    ) : SearchState
}