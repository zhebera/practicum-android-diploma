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

            val isFilterNotEmpty =
                !filter?.regionName.isNullOrEmpty() ||
                    !filter?.industryName.isNullOrEmpty() ||
                    !filter?.salary.isNullOrEmpty() ||
                    !filter?.countryName.isNullOrEmpty() ||
                    filter?.onlyWithSalary == true

            val newFilterState = if (isFilterNotEmpty) {
                FilterState.Filter(
                    filter?.salary,
                    filter?.onlyWithSalary,
                    filter?.industryName,
                    filter?.countryName,
                    filter?.regionName
                )
            } else {
                FilterState.Filter("", false, "", "", "")
            }

            _filterState.postValue(newFilterState)
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
