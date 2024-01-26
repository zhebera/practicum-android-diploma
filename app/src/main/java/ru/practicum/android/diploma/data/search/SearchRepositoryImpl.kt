package ru.practicum.android.diploma.data.search

import android.content.Context
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
import ru.practicum.android.diploma.util.Resource

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacanciesConverter: VacanciesConverter,
    context: Context
) : SearchRepository {
    private val badConnection by lazy {
        context.getString(R.string.bad_connection)
    }
    private val serverError by lazy {
        context.getString(R.string.server_error)
    }
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
}
