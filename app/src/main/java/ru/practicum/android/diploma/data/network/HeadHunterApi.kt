package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.data.response.VacanciesResponse
import ru.practicum.android.diploma.data.dto.VacancyDescriptionResponse

interface HeadHunterApi {

    @GET("/vacancies")
    suspend fun getVacancies(
        @Query("text") vacancy: String
    ): VacanciesResponse

//    @Headers(
//        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
//        "HH-User-Agent: Career Key (averkieva10012017@yandex.ru)"
//    )
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancyDescription(@Path("vacancy_id") vacancyId: String): VacancyDescriptionResponse
}
