package ru.practicum.android.diploma.ui.regions.viewmodel

import android.graphics.Region

sealed interface RegionsState {

    object Loading : RegionsState
    data class Error(val message: String) : RegionsState
    data class Content(val data: List<Region>) : RegionsState
}
