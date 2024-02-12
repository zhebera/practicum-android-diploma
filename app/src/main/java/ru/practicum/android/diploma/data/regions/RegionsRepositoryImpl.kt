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
        if (areaId.isNullOrBlank()) {
            val response = networkClient.doRequest(AllRegionsRequest())

            when (response.resultCode) {
                ResponseCode.NETWORK_FAILED -> { emit(Resource.Error(message = badConnection)) }
                ResponseCode.SUCCESS -> {
                    with(response as RegionResponse) {
                        val countriesData = regionsConverter.convertRegions(this)
                        val mergedList = ArrayList<Region>()

                        val filteredData = countriesData.filter {
                            it.id != "1001"
                        }

                        filteredData.forEach { country ->
                            mergedList.addAll(country.includedRegions)

                            country.includedRegions.forEach { region ->
                                mergedList.addAll(region.includedRegions)
                            }
                        }
                        emit(Resource.Success(mergedList))
                    }
                }
                else -> emit(Resource.Error(message = serverError))
            }
        } else {
            val response = networkClient.doRequest(CountryRegionsRequest(areaId))
            when (response.resultCode) {
                ResponseCode.NETWORK_FAILED -> { emit(Resource.Error(message = badConnection)) }

                ResponseCode.SUCCESS -> {
                    with(response as RegionResponse) {
                        val regionsData = regionsConverter.convertRegions(this)
                        val mergedList = ArrayList<Region>(regionsData)

                        regionsData.forEach {
                            mergedList.addAll(it.includedRegions)
                        }
                        emit(Resource.Success(mergedList))
                    }
                }

                else -> emit(Resource.Error(message = serverError))
            }
        }
    }
}
