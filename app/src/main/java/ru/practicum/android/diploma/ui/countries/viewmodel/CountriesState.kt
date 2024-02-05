package ru.practicum.android.diploma.ui.countries.viewmodel

import ru.practicum.android.diploma.domain.models.Country

sealed interface CountriesState {

    object Loading: CountriesState
    object Error : CountriesState
    data class Content(
        val data: List<Country>
    ) : CountriesState
}
