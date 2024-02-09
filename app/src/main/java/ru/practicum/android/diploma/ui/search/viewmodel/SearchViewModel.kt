package ru.practicum.android.diploma.ui.search.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.search.SearchInteractor
import ru.practicum.android.diploma.domain.api.sharedpreferences.SharedPreferencesInteractor
import ru.practicum.android.diploma.domain.models.FilterModel
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.util.SEARCH_MAP_KEY_AREA
import ru.practicum.android.diploma.util.SEARCH_MAP_KEY_INDUSTRY
import ru.practicum.android.diploma.util.SEARCH_MAP_KEY_SALARY
import ru.practicum.android.diploma.util.SEARCH_MAP_KEY_TEXT
import ru.practicum.android.diploma.util.SEARCH_MAP_KEY_WITH_SALARY
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(
    private val searchInteractor: SearchInteractor,
    private val sharedPreferencesInteractor: SharedPreferencesInteractor
) : ViewModel() {

    private val _searchState = MutableLiveData<SearchState>()
    val searchState: LiveData<SearchState> = _searchState
    private var latestSearchTrack: String? = null

    init {
        _searchState.postValue(SearchState.Empty)
    }

    private fun getVacancies(options: HashMap<String, String>) {
        _searchState.postValue(SearchState.Loading)

        viewModelScope.launch {
            searchInteractor.searchVacancies(options).collect { pair ->
                processResult(pair.first, pair.second)
            }
        }
    }

    private val searchingDebounce = debounce<HashMap<String, String>>(
        SEARCH_DEBOUNCE_DELAY,
        viewModelScope,
        true
    ) { options ->
        getVacancies(options)
    }

    fun searchDebounce(changedText: String, newFilter: Boolean) {
        if (latestSearchTrack == changedText && !newFilter) return
        val filter = sharedPreferencesInteractor.getFilter()
        val request = getRequestWithFilter(changedText, filter)
        searchingDebounce(request)
        latestSearchTrack = changedText
    }

    private fun getRequestWithFilter(changedText: String, filter: FilterModel?): HashMap<String, String> {
        val hashMap = HashMap<String, String>()
        hashMap[SEARCH_MAP_KEY_TEXT] = changedText
        if (filter != null) {
            if (!filter.industry?.id.isNullOrEmpty()) {
                hashMap[SEARCH_MAP_KEY_INDUSTRY] = filter.industry?.id.toString()
            }
            if (filter.onlyWithSalary != null) {
                hashMap[SEARCH_MAP_KEY_WITH_SALARY] = filter.onlyWithSalary.toString()
            }
            if (!filter.salary.isNullOrEmpty()) {
                hashMap[SEARCH_MAP_KEY_SALARY] = filter.salary
            }
            if (!filter.region?.id.isNullOrEmpty()) {
                hashMap[SEARCH_MAP_KEY_AREA] = filter.region?.id.toString()
            }
        }
        return hashMap
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

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
