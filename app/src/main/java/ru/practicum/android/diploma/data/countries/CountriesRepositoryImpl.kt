package ru.practicum.android.diploma.data.countries

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.converters.CountriesConverter
import ru.practicum.android.diploma.data.dto.ResponseCode
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.request.CountriesRequest
import ru.practicum.android.diploma.data.response.CountriesResponse
import ru.practicum.android.diploma.domain.api.countries.CountriesRepository
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.util.Resource

class CountriesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val countriesConverter: CountriesConverter,
    context: Context
) : CountriesRepository {
    private val badConnection by lazy {
        context.getString(R.string.bad_connection)
    }
    private val serverError by lazy {
        context.getString(R.string.server_error)
    }

    override fun getCountries(): Flow<Resource<List<Country>>> = flow {
        val response = networkClient.doRequest(CountriesRequest())
        when (response.resultCode) {
            ResponseCode.NETWORK_FAILED -> {
                emit(Resource.Error(message = badConnection))
            }

            ResponseCode.SUCCESS -> {
                with(response as CountriesResponse) {
                    val data = countriesConverter.map(response).sortedBy { it.name }
                    emit(Resource.Success(data))
                }
            }

            else -> emit(Resource.Error(message = serverError))
        }
    }
}
