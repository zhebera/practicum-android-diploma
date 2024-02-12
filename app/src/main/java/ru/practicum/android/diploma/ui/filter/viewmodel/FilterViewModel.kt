package ru.practicum.android.diploma.ui.filter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.sharedpreferences.SharedPreferencesInteractor
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.FilterModel
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.Region

class FilterViewModel(
    private val sharedPreferencesInteractor: SharedPreferencesInteractor
) : ViewModel() {
    fun getFilter(): FilterModel? {
        return sharedPreferencesInteractor.getFilter()
    }

    fun clearFilter() {
        viewModelScope.launch {
            sharedPreferencesInteractor.clearFilter()
        }
    }

    fun saveFilter(
        country: Country?,
        region: Region?,
        industry: Industry?,
        salary: String?,
        onlyWithSalary: Boolean?
    ) {
        viewModelScope.launch {
            sharedPreferencesInteractor.saveFilter(
                country,
                region,
                industry,
                salary,
                onlyWithSalary
            )
        }
    }
}
