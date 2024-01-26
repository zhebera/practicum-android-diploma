package ru.practicum.android.diploma.data.response

import com.google.gson.annotations.SerializedName

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
    val address: AddressResponse?,
    val employer: EmployerResponse,
    val department: DepartmentResponse?,
    val salary: SalaryResponse?
)

class AreaResponse(
    val id: String,
    val name: String,
    val url: String
)

class AddressResponse(
    val building: String?,
    val city: String?,
    val description: String?,
    val lat: Double?,
    val lng: Double?,
    val street: String?
)

class EmployerResponse(
    @SerializedName("alternate_url") val alternateUrl: String,
    val blacklisted: Boolean,
    val id: String,
    @SerializedName("logo_urls") val logoUrls: LogoUrlsResponse,
    val name: String,
    val trusted: Boolean,
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
