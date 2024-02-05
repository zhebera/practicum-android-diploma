package ru.practicum.android.diploma.data.industries

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.converters.IndustriesConverter
import ru.practicum.android.diploma.data.dto.ResponseCode
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.request.IndustriesRequest
import ru.practicum.android.diploma.data.response.IndustriesResponse
import ru.practicum.android.diploma.domain.api.industries.IndustriesRepository
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

class IndustriesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val industriesDbConverter: IndustriesConverter,
    context: Context
) : IndustriesRepository {

    private val badConnection by lazy {
        context.getString(R.string.bad_connection)
    }
    private val serverError by lazy {
        context.getString(R.string.server_error)
    }

    override fun getIndustries(): Flow<Resource<List<Industry>>> = flow {
        val response = networkClient.doRequest(IndustriesRequest())
        when (response.resultCode) {
            ResponseCode.NETWORK_FAILED -> {
                emit(Resource.Error(message = badConnection))
            }

            ResponseCode.SUCCESS -> {
                with(response as IndustriesResponse) {
                    val data = industriesDbConverter.map(response)
                        .sortedBy { it.name }
                    emit(Resource.Success(data))
                }
            }

            else -> emit(Resource.Error(message = serverError))
        }
    }
}
