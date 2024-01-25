package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.practicum.android.diploma.data.response.VacanciesResponse

interface HeadHunterApi {

    @GET("/vacancies")
    suspend fun getVacancies(
        @Query("text") vacancy: String
    ): VacanciesResponse
}
