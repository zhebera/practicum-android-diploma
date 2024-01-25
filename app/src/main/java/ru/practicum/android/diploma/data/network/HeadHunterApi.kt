package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.practicum.android.diploma.data.dto.VacancyDescriptionResponse

interface HeadHunterApi {

    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancyDescription(@Path("vacancy_id") vacancyId: String): VacancyDescriptionResponse
}
