package ru.practicum.android.diploma.ui.favourite.viewmodel

import ru.practicum.android.diploma.domain.models.VacancyDescription

sealed interface FavouriteState {

    object Empty : FavouriteState
    data class Content(val data: List<VacancyDescription>) : FavouriteState
}
