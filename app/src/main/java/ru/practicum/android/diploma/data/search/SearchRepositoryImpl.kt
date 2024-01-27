package ru.practicum.android.diploma.data.search

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converters.VacanciesConverter
import ru.practicum.android.diploma.data.dto.VacancyDescriptionRequest
import ru.practicum.android.diploma.data.dto.VacancyDescriptionResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.models.VacancyDescription
import ru.practicum.android.diploma.util.RESULT_CODE_200
import ru.practicum.android.diploma.util.RESULT_CODE_MINUS
import ru.practicum.android.diploma.util.Resource

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacanciesConverter: VacanciesConverter
) : SearchRepository {
    override fun getVacancyDescription(vacancyId: String): Flow<Resource<VacancyDescription>> = flow {
         val response = networkClient.doRequest(VacancyDescriptionRequest(vacancyId))
         when (response.resultCode) {
             RESULT_CODE_MINUS -> {
                 emit(Resource.Error(message = "Проверьте подключение к интернету"))
             }
             RESULT_CODE_200 -> {
                 with(response as VacancyDescriptionResponse) {
                     val data = vacanciesConverter.convertVacancyDescription(response)
                     emit(Resource.Success(data))
                 }
             }
             else -> emit(Resource.Error(message = "Ошибка сервера"))
         }
    }

}
