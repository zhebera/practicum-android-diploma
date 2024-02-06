package ru.practicum.android.diploma.ui.filter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.sharedpreferences.SharedPreferencesInteractor
import ru.practicum.android.diploma.domain.models.FilterModel

class FilterViewModel(
    private val sharedPreferencesInteractor: SharedPreferencesInteractor
) : ViewModel() {

    private val _filterState = MutableLiveData<FilterModel?>()
    val filterState: LiveData<FilterModel?> = _filterState

    init {
        viewModelScope.launch { getFilter() }
    }

    private fun getFilter() {
        val filter = sharedPreferencesInteractor.getFilter()
        _filterState.postValue(filter)

    }

    fun clearFilter() {
        viewModelScope.launch {
            sharedPreferencesInteractor.clearFilter()
            getFilter()
        }
    }

    fun setSalary(salary: String) {
        viewModelScope.launch {
            sharedPreferencesInteractor.setSalary(salary)
            getFilter()
        }
    }

    fun setSalaryCheckbox(salary: Boolean) {
        viewModelScope.launch {
            sharedPreferencesInteractor.setSalaryCheckbox(salary)
            getFilter()
        }
    }
}
