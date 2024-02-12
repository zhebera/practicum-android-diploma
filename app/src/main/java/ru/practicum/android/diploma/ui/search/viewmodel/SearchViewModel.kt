package ru.practicum.android.diploma.ui.search.viewmodel

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

    private val _filterState = MutableLiveData<Boolean>()
    val filterState: LiveData<Boolean> = _filterState

    private var latestSearchVacancy: String? = null
    private var foundVacancies: Int? = null
    private var currentPage: Int? = null
    private var maxPages: Int? = null
    private var newItemList = true

    init {
        _searchState.postValue(SearchState.Default)
    }

    private fun getVacancies(options: HashMap<String, String>) {
        if (newItemList) {
            _searchState.postValue(SearchState.Loading)
        }

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

    fun getCountVacancies() = foundVacancies

    fun searchDebounce(changedText: String, newFilter: Boolean) {
        if (latestSearchVacancy == changedText && !newFilter) return
        val filter = sharedPreferencesInteractor.getFilter()
        val request = getRequestWithFilter(changedText, filter)
        newItemList = true
        searchingDebounce(request)
        latestSearchVacancy = changedText
    }

    private fun getRequestWithFilter(changedText: String, filter: FilterModel?): HashMap<String, String> {
        val hashMap = HashMap<String, String>()
        hashMap[PER_PAGE_KEY] = PER_PAGE_COUNT.toString()
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
            } else if (!filter.country?.id.isNullOrEmpty()) {
                hashMap[SEARCH_MAP_KEY_AREA] = filter.country?.id.toString()
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
            foundVacancies = data.found
            currentPage = data.page
            maxPages = data.pages
            _searchState.postValue(SearchState.Content(data, currentPage))
        }
    }

    fun getFilterState() {
        _filterState.postValue(checkFilter())
    }

    private fun checkFilter(): Boolean {
        val filter = sharedPreferencesInteractor.getFilter()
        return if (filter == null) {
            false
        } else {
            (!filter.country?.id.isNullOrEmpty()
                || !filter.region?.id.isNullOrEmpty()
                || !filter.industry?.id.isNullOrEmpty()
                || !filter.salary.isNullOrEmpty()
                || filter.onlyWithSalary == true)
        }
    }

    fun getNextPageData() {
        if (currentPage == maxPages) {
            return
        } else {
            val filter = sharedPreferencesInteractor.getFilter()
            val request = getRequestWithFilter(latestSearchVacancy.toString(), filter)
            request[PAGE] = currentPage?.plus(1).toString()
            newItemList = false
            searchingDebounce(request)
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val PER_PAGE_COUNT = 20
        const val PER_PAGE_KEY = "per_page"
        private const val PAGE = "page"
    }
}
