package ru.practicum.android.diploma.ui.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.details.DetailsInteractor
import ru.practicum.android.diploma.domain.api.favourite.FavouriteInteractor
import ru.practicum.android.diploma.domain.api.search.SearchInteractor
import ru.practicum.android.diploma.domain.models.VacancyDescription

class VacancyDescriptionViewModel(
    private val interactor: SearchInteractor,
    private val detailsInteractor: DetailsInteractor,
    private val favouriteInteractor: FavouriteInteractor
) : ViewModel() {

    private var vacancy: VacancyDescription? = null

    private val _vacancyDescriptionState = MutableLiveData<VacancyDescriptionState>()
    val vacancyDescriptionState: LiveData<VacancyDescriptionState> = _vacancyDescriptionState

    private val _vacancyDescriptionDbState = MutableLiveData<VacancyDescriptionState>()
    val vacancyDescriptionDbState: LiveData<VacancyDescriptionState> = _vacancyDescriptionDbState

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private var counter = 0

    fun getVacancyDescription(vacancyId: String) {
        _vacancyDescriptionState.postValue(VacancyDescriptionState.Loading)

        viewModelScope.launch {
            interactor.getVacancyDescription(vacancyId)
                .collect { pair ->
                    processResult(vacancyId, pair.first, pair.second)
                }
        }
    }

    private fun renderFavorite(favorite: Boolean) {
        _isFavorite.postValue(favorite)
    }

    fun changeFavourite() {
        viewModelScope.launch {
            val favorite = _isFavorite.value ?: false
            if (vacancy != null) {
                if (favorite) {
                    favouriteInteractor.removeVacancy(vacancy!!)
                } else {
                    favouriteInteractor.addVacancy(vacancy!!)
                }
                renderFavorite(!favorite)
            }
        }
    }

    fun checkFavorite() {
        viewModelScope.launch {
            if (vacancy != null) {
                favouriteInteractor.checkVacancy(vacancy!!.id).collect {
                    renderFavorite(it)
                }
            }
        }
    }

    private fun processResult(vacancyId: String, data: VacancyDescription?, message: String?) {
        if (data == null) {
            _vacancyDescriptionState.postValue(VacancyDescriptionState.Error(message = message ?: "Неизвестная ошибка"))
            getVacancyById(vacancyId)
        } else {
            _vacancyDescriptionState.postValue(VacancyDescriptionState.Content(data))
            vacancy = data
        }
    }

    private fun getVacancyById(vacancyId: String) {
        viewModelScope.launch {
            favouriteInteractor.getVacancyDescriptionById(vacancyId)
                .collect { pair ->
                    processResult(vacancyId, pair.first, pair.second)
                }
        }
    }

    fun shareLink(link: String) {
        detailsInteractor.shareLink(link)
    }

    fun openEmail(email: String) {
        detailsInteractor.openEmail(email)
    }

    fun doCall(phone: String) {
        detailsInteractor.doCall(phone)
    }
}
