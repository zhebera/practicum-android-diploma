package ru.practicum.android.diploma.ui.favourite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.favourite.FavouriteInteractor
import ru.practicum.android.diploma.domain.models.VacancyDescription

class FavouriteViewModel(private val favouriteInteractor: FavouriteInteractor) : ViewModel() {

    private val _favouriteVacancies = MutableLiveData<FavouriteState>()
    val favouriteVacancies: LiveData<FavouriteState> = _favouriteVacancies

    fun getVacancies() {
        viewModelScope.launch {
            favouriteInteractor.getAllVacancies().collect(::renderState)
        }
    }

    private fun renderState(playlists: List<VacancyDescription>) {
        if (playlists.isNullOrEmpty())
            _favouriteVacancies.postValue(FavouriteState.Empty)
        else
            _favouriteVacancies.postValue(FavouriteState.Content(playlists))
    }
}
