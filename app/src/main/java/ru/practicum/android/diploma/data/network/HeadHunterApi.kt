package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.IndustriesDto
import ru.practicum.android.diploma.data.dto.VacancyDescriptionResponse
import ru.practicum.android.diploma.data.response.CountriesResponse
import ru.practicum.android.diploma.data.response.VacanciesResponse

interface HeadHunterApi {

    @GET("/vacancies")
    suspend fun getVacancies(
        @Query("text") vacancy: String
    ): VacanciesResponse

    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancyDescription(@Path("vacancy_id") vacancyId: String): VacancyDescriptionResponse

    @GET("/industries")
    suspend fun getIndustries(): ArrayList<IndustriesDto>

    @GET("/areas/countries")
    suspend fun getCountries(): CountriesResponse
}
