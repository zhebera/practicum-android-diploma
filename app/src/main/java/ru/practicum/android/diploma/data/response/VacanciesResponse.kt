package ru.practicum.android.diploma.data.response

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.Response

class VacanciesResponse(
    val found: Int,
    val items: List<VacancyResponse>,
    val page: Int,
    val pages: Int,
    @SerializedName("per_page") val perPage: Int,
) : Response()

class VacancyResponse(
    val id: String,
    val name: String,
    val area: AreaResponse,
    val employer: EmployerResponse,
    val department: DepartmentResponse?,
    val salary: SalaryResponse?
)

class AreaResponse(
    val id: String,
    val name: String,
    val url: String
)

class EmployerResponse(
    @SerializedName("alternate_url") val alternateUrl: String,
    val id: String,
    @SerializedName("logo_urls") val logoUrls: LogoUrlsResponse,
    val name: String,
    val url: String
)

class LogoUrlsResponse(
    val original: String
)

class DepartmentResponse(
    val id: String,
    val name: String
)

class SalaryResponse(
    val currency: String?,
    val from: Int?,
    val gross: Boolean?,
    val to: Int?
)
