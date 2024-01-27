package ru.practicum.android.diploma.data.search

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.converters.VacanciesConverter
import ru.practicum.android.diploma.data.dto.ResponseCode
import ru.practicum.android.diploma.data.dto.VacancyDescriptionRequest
import ru.practicum.android.diploma.data.dto.VacancyDescriptionResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.models.VacancyDescription
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

    override fun getVacancyDescription(vacancyId: String): Flow<Resource<VacancyDescription>> = flow {
        val response = networkClient.doRequest(VacancyDescriptionRequest(vacancyId))
        when (response.resultCode) {
            ResponseCode.NETWORK_FAILED -> {
                emit(Resource.Error(message = badConnection))
            }

            ResponseCode.SUCCESS -> {
                with(response as VacancyDescriptionResponse) {
                    val data = vacanciesConverter.convertVacancyDescription(response)
                    emit(Resource.Success(data))
                }
            }

            else -> emit(Resource.Error(message = serverError))
        }
    }

}
