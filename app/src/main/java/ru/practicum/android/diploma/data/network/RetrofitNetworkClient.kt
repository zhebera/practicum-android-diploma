package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacancyDescriptionRequest
import ru.practicum.android.diploma.util.RESULT_CODE_200
import ru.practicum.android.diploma.util.RESULT_CODE_MINUS

class RetrofitNetworkClient(
    private val hhApi: HeadHunterApi,
    private val context: Context
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = RESULT_CODE_MINUS }
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = when (dto) {
                    is VacancyDescriptionRequest -> hhApi.getVacancyDescription(vacancyId = dto.vacancyId)
                    else -> Response().apply { resultCode = RESULT_CODE_200 }
                }
                response.apply { resultCode = RESULT_CODE_200 }
            } catch (e: Throwable) {
                Response().apply { resultCode = RESULT_CODE_200 }
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
