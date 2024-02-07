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
    private var industries: MutableList<Industry>? = null

    private val _checkedIndustries = MutableLiveData<List<Boolean>>()
    val checkedIndustries: LiveData<List<Boolean>> = _checkedIndustries

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
            industries = data.toMutableList()
        }
    }

    fun changeChecks(industry: Industry) {
        val index = industries!!.indexOf(industry.copy(isChecked = !industry.isChecked))
        industries!!.set(index, industry)
        _checkedIndustries.postValue(industries!!.map { it.isChecked })
    }

    fun getIndustriesList() = industries
}
