package ru.practicum.android.diploma.ui.regions.viewmodel

import android.graphics.Region
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.regions.RegionsInteractor
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.ui.search.viewmodel.SearchState

class RegionsViewModel(private val regionsInteractor: RegionsInteractor) : ViewModel() {

    private val _regions = MutableLiveData<RegionsState>()
    val regions: LiveData<RegionsState> = _regions

    fun getRegions() {
        viewModelScope.launch {
            regionsInteractor.getRegions().collect {
                processResult(it.first, it.second)
            }
        }
    }

    private fun processResult(data: List<Region>?, message: String?) {
        if (data != null) {
            _regions.postValue(RegionsState.Content(data))
        } else {
            _regions.postValue(RegionsState.Error(message.toString()))
        }
    }
}
