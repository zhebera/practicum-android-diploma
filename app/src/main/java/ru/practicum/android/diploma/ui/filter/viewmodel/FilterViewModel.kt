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

    fun saveFilter(
        countryName: String?,
        countryId: String?,
        regionName: String?,
        regionId: String?,
        industryName: String?,
        industryId: String?,
        salary: String?,
        onlyWithSalary: Boolean?
    ) {
        viewModelScope.launch {
            sharedPreferencesInteractor.saveFilter(
                countryName,
                countryId,
                regionName,
                regionId,
                industryName,
                industryId,
                salary,
                onlyWithSalary
            )
        }
    }
}
