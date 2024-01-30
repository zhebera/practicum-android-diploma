package ru.practicum.android.diploma.ui.favourite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.favourite.FavouriteInteractor
import ru.practicum.android.diploma.domain.models.VacancyDescription
import java.lang.NullPointerException

class FavouriteViewModel(private val favouriteInteractor: FavouriteInteractor) : ViewModel() {

    private val _favouriteVacancies = MutableLiveData<FavouriteState>()
    val favouriteVacancies: LiveData<FavouriteState> = _favouriteVacancies
    fun getAllFavouriteVacancies() {
        viewModelScope.launch {
            favouriteInteractor.getAllVacancies().collect(::renderState)
        }
    }

    private fun renderState(favouriteVacancies: List<VacancyDescription>) {
        try {
            if (favouriteVacancies.isEmpty()) {
                _favouriteVacancies.postValue(FavouriteState.Empty)
            } else {
                _favouriteVacancies.postValue(FavouriteState.Content(favouriteVacancies))
            }
        } catch (e: NullPointerException) {
            _favouriteVacancies.postValue(FavouriteState.Error)
        }
    }
}
