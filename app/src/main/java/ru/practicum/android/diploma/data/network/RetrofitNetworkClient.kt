package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.ResponseCode
import ru.practicum.android.diploma.data.dto.VacancyDescriptionRequest
import ru.practicum.android.diploma.data.request.CountriesRequest
import ru.practicum.android.diploma.data.request.IndustriesRequest
import ru.practicum.android.diploma.data.request.SearchRequest
import ru.practicum.android.diploma.data.response.IndustriesResponse

class RetrofitNetworkClient(
    private val hhApi: HeadHunterApi,
    private val context: Context
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = ResponseCode.NETWORK_FAILED }
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = when (dto) {
                    is SearchRequest -> hhApi.getVacancies(vacancy = dto.vacancy)
                    is VacancyDescriptionRequest -> hhApi.getVacancyDescription(vacancyId = dto.vacancyId)
                    is IndustriesRequest -> try {
                        val resp = hhApi.getIndustries()
                        val response = IndustriesResponse(resp)
                        response.apply {
                            resultCode = ResponseCode.SUCCESS
                        }
                    } catch (e: Exception) {
                        Response().apply { resultCode = ResponseCode.BAD_ARGUMENT }
                    }

                    is CountriesRequest -> hhApi.getCountries()
                    else -> Response().apply { resultCode = ResponseCode.BAD_ARGUMENT }
                }
                response.apply { resultCode = ResponseCode.SUCCESS }
            } catch (e: HttpException) {
                when (e.code()) {
                    ResponseCode.NOT_FOUND -> Response().apply { resultCode = ResponseCode.NOT_FOUND }
                    ResponseCode.BAD_AUTHORIZATION -> Response().apply {
                        resultCode = ResponseCode.BAD_AUTHORIZATION
                    }

                    else -> Response().apply { resultCode = ResponseCode.SERVER_FAILED }
                }
            }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}
