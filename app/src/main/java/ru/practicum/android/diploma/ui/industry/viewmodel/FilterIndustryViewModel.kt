package ru.practicum.android.diploma.ui.industry.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.industries.IndustriesInteractor
import ru.practicum.android.diploma.domain.models.Industry

class FilterIndustryViewModel(private val industriesInteractor: IndustriesInteractor) : ViewModel() {

    private val _industriesState = MutableLiveData<FilterIndustriesState>()
    val industriesState: LiveData<FilterIndustriesState> = _industriesState
    private var industries: List<Industry>? = null

    fun getIndustries() {
        _industriesState.postValue(FilterIndustriesState.Loading)

        viewModelScope.launch {
            industriesInteractor.getIndustries()
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
        }
    }

    private fun processResult(data: List<Industry>?, message: String?) {
        if (data.isNullOrEmpty()) {
            _industriesState.postValue(FilterIndustriesState.Error(message = message ?: "Неизвестная ошибка"))
        } else {
            _industriesState.postValue(FilterIndustriesState.Content(data))
            industries = data
        }
    }

    fun getIndustriesList() = industries
}
