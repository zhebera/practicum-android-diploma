package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.IndustriesDto
import ru.practicum.android.diploma.data.dto.VacancyDescriptionResponse
import ru.practicum.android.diploma.data.response.CountryResponse
import ru.practicum.android.diploma.data.response.RegionResponse
import ru.practicum.android.diploma.data.response.VacanciesResponse

interface HeadHunterApi {

    @GET("/vacancies")
    suspend fun getVacancies(
        @QueryMap options: Map<String, String>
    ): VacanciesResponse

    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancyDescription(@Path("vacancy_id") vacancyId: String): VacancyDescriptionResponse

    @GET("/areas")
    suspend fun getAllRegions(): ArrayList<RegionResponse>

    @GET("/areas/{area_id}")
    suspend fun getCountryRegions(
        @Query("text") countryId: String
    ): RegionResponse

    @GET("/industries")
    suspend fun getIndustries(): ArrayList<IndustriesDto>

    @GET("/areas/countries")
    suspend fun getCountries(): List<CountryResponse>
}
