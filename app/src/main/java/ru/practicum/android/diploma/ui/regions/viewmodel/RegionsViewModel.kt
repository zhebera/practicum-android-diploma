package ru.practicum.android.diploma.ui.regions.viewmodel

import android.graphics.Region
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.regions.RegionsInteractor

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
