package ru.practicum.android.diploma.ui.industry.viewmodel

import ru.practicum.android.diploma.domain.models.Industry

sealed interface FilterIndustriesState {

    object Loading : FilterIndustriesState

    data class Content(
        val data: List<Industry>
    ) : FilterIndustriesState

    data class Error(
        val message: String
    ) : FilterIndustriesState
}
