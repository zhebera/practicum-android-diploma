package ru.practicum.android.diploma.data.regions

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.converters.RegionsConverter
import ru.practicum.android.diploma.data.dto.ResponseCode
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.request.AllRegionsRequest
import ru.practicum.android.diploma.data.request.CountryRegionsRequest
import ru.practicum.android.diploma.data.response.RegionResponse
import ru.practicum.android.diploma.domain.api.regions.RegionsRepository
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.util.Resource

class RegionsRepositoryImpl(
    private val networkClient: NetworkClient,
    private val regionsConverter: RegionsConverter,
    context: Context
) : RegionsRepository {

    private val badConnection by lazy {
        context.getString(R.string.bad_connection)
    }
    private val serverError by lazy {
        context.getString(R.string.server_error)
    }

    override fun getRegions(areaId: String?): Flow<Resource<List<Region>>> = flow {
        val response = if (areaId == null) {
            networkClient.doRequest(AllRegionsRequest())
        } else {
            networkClient.doRequest(CountryRegionsRequest(areaId))
        }

        when (response.resultCode) {
            ResponseCode.NETWORK_FAILED -> {
                emit(Resource.Error(message = badConnection))
            }

            ResponseCode.SUCCESS -> {
                with(response as RegionResponse) {
                    val data = regionsConverter.convertRegions(this)
                    emit(Resource.Success(data))
                }
            }

            else -> emit(Resource.Error(message = serverError))
        }
    }
}
