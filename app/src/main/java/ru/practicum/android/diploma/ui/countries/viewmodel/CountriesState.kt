package ru.practicum.android.diploma.ui.countries.viewmodel

import ru.practicum.android.diploma.domain.models.Country

sealed interface CountriesState {

    object Empty : CountriesState

    data class Content(
        val data: List<Country>
    ) : CountriesState

    data class Error(
        val message: String
    ) : CountriesState
}
