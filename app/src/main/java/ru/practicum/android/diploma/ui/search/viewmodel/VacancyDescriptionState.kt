package ru.practicum.android.diploma.ui.search.viewmodel

import ru.practicum.android.diploma.domain.models.VacancyDescription

sealed interface VacancyDescriptionState {

    object Loading : VacancyDescriptionState

    data class Content(
        val data: VacancyDescription
    ) : VacancyDescriptionState

    data class Error(
        val message: String
    ) : VacancyDescriptionState
}
