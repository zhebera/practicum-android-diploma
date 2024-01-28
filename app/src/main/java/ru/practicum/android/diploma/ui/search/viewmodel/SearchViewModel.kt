package ru.practicum.android.diploma.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.search.SearchInteractor
import ru.practicum.android.diploma.domain.models.Vacancies

class SearchViewModel(private val searchInteractor: SearchInteractor) : ViewModel() {

    private val _searchState = MutableLiveData<SearchState>()
    val searchState: LiveData<SearchState> = _searchState

    fun getVacancies(vacancy: String) {
        _searchState.postValue(SearchState.Loading)

        viewModelScope.launch {
            searchInteractor.searchVacancies(vacancy).collect { pair ->
                processResult(pair.first, pair.second)
            }
        }
    }

    private fun processResult(data: Vacancies?, message: String?) {
        if (data != null) {
            setData(data)
        } else {
            _searchState.postValue(SearchState.Error(message.toString()))
        }
    }

    private fun setData(data: Vacancies) {
        if (data.found == 0) {
            _searchState.postValue(SearchState.Empty)
        }
        if (data.found != 0 && data.items.isNotEmpty()) {
            _searchState.postValue(SearchState.Content(data))
        }
    }
}
