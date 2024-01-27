package ru.practicum.android.diploma.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.SearchInteractor
import ru.practicum.android.diploma.domain.models.VacancyDescription

class VacancyDescriptionViewModel(
    private val interactor: SearchInteractor
) : ViewModel() {

    private var _vacancyDescriptionState = MutableLiveData<VacancyDescriptionState>()
    val vacancyDescriptionState: LiveData<VacancyDescriptionState> get() = _vacancyDescriptionState

    fun getVacancyDescription(vacancyId: String) {
        _vacancyDescriptionState.postValue(VacancyDescriptionState.Loading)

        viewModelScope.launch {
            interactor.getVacancyDescription(vacancyId)
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
        }

    }

    private fun processResult(data: VacancyDescription?, message: String?) {
        if (data == null) {
            _vacancyDescriptionState.postValue(VacancyDescriptionState.Error(message = message ?: "Неизвестная ошибка"))
        } else {
            _vacancyDescriptionState.postValue(VacancyDescriptionState.Content(data))
        }

    }
}
