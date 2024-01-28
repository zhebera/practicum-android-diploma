package ru.practicum.android.diploma.data.search

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.converters.VacanciesConverter
import ru.practicum.android.diploma.data.dto.ResponseCode
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.request.SearchRequest
import ru.practicum.android.diploma.data.response.VacanciesResponse
import ru.practicum.android.diploma.domain.api.search.SearchRepository
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacanciesConverter: VacanciesConverter,
    context: Context,
    private val savedSearchHistory: SharedPreferences,
    private val gson: Gson
) : SearchRepository {
    private val badConnection by lazy {
        context.getString(R.string.bad_connection)
    }
    private val serverError by lazy {
        context.getString(R.string.server_error)
    }
    private var vacancies = ArrayList<Vacancy>()

    override fun searchVacancies(vacancy: String): Flow<Resource<Vacancies>> = flow {
        val response = networkClient.doRequest(SearchRequest(vacancy))
        when (response.resultCode) {
            ResponseCode.NETWORK_FAILED -> {
                emit(Resource.Error(badConnection))
            }

            ResponseCode.SUCCESS -> {
                with(response as VacanciesResponse) {
                    val data = vacanciesConverter.convert(response)
                    emit(Resource.Success(data))
                }
            }

            else -> emit(Resource.Error(serverError))
        }
    }

    override fun add(newIt: Vacancy) {
        val json = savedSearchHistory.getString(SEARCH_SHARED_PREFERENCE, "")
        if (json != null && json.isNotEmpty() && vacancies.isEmpty() && savedSearchHistory.contains(
                SEARCH_SHARED_PREFERENCE
            )
        ) {
            val type = object : TypeToken<ArrayList<Vacancy>>() {}.type
            vacancies = gson.fromJson(json, type)
        }
        if (vacancies.contains(newIt)) {
            vacancies.remove(newIt)
            vacancies.add(number_0, newIt)
        } else {
            if (vacancies.size < number_10)
                vacancies.add(number_0, newIt)
            else {
                vacancies.removeAt(number_9)
                vacancies.add(number_0, newIt)
            }
        }
        save()
    }

    private fun save() {
        var json = ""
        json = gson.toJson(vacancies)
        savedSearchHistory.edit()
            .clear()
            .putString(SEARCH_SHARED_PREFERENCE, json)
            .apply()
    }

    companion object {
        const val SEARCH_SHARED_PREFERENCE = "search"
        const val number_10 = 10
        const val number_9 = 9
        const val number_0 = 0
    }
}
