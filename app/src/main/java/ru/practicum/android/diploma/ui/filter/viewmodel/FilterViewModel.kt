package ru.practicum.android.diploma.ui.filter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.sharedpreferences.SharedPreferencesInteractor

class FilterViewModel(
    private val sharedPreferencesInteractor: SharedPreferencesInteractor
) : ViewModel() {

    private val _filterState = MutableLiveData<FilterState>()
    val filterState: LiveData<FilterState> = _filterState

    fun getFilter() {
        viewModelScope.launch {
            val filter = sharedPreferencesInteractor.getFilter()
            if (filter != null && (
                    !filter.regionName.isNullOrEmpty() ||
                        !filter.industryName.isNullOrEmpty() ||
                        !filter.salary.isNullOrEmpty() ||
                        !filter.countryName.isNullOrEmpty() ||
                        filter.onlyWithSalary == true
                    )
            ) {
                _filterState.postValue(
                    FilterState.Filter(
                        filter.salary,
                        filter.onlyWithSalary,
                        filter.industryName,
                        filter.countryName,
                        filter.regionName
                    )
                )
            } else {
                _filterState.postValue(
                    FilterState.Filter(
                        "",
                        false,
                        "",
                        "",
                        ""
                    )
                )
            }
        }
    }

    fun clearFilter() {
        viewModelScope.launch {
            sharedPreferencesInteractor.clearFilter()
            _filterState.postValue(FilterState.ClearFilter)
        }
    }

    fun setSalary(salary: String) {
        viewModelScope.launch {
            sharedPreferencesInteractor.setSalary(salary)
            _filterState.postValue(FilterState.SetSettings)
        }
    }

    fun setSalaryCheckbox(salary: Boolean) {
        viewModelScope.launch {
            sharedPreferencesInteractor.setSalaryCheckbox(salary)
            _filterState.postValue(FilterState.SetSettings)
        }
    }
}
