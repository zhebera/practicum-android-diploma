package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.ResponseCode
import ru.practicum.android.diploma.data.request.SearchRequest
import ru.practicum.android.diploma.data.response.Response

class RetrofitNetworkClient(
    private val hhApi: HeadHunterApi,
    private val context: Context
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = ResponseCode.NETWORK_FAILED }
        }
        if (dto !is SearchRequest) {
            return Response().apply { resultCode = ResponseCode.BAD_ARGUMENT }
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = when (dto) {
                    is SearchRequest -> hhApi.getVacancies(vacancy = dto.vacancy)
                    else -> Response().apply { resultCode = ResponseCode.SERVER_FAILED }
                }
                response.apply { resultCode = ResponseCode.SUCCESS }
            } catch (e: Throwable) {
                Response().apply { resultCode = ResponseCode.SERVER_FAILED }
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
