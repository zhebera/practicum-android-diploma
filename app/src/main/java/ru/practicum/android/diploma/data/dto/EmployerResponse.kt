package ru.practicum.android.diploma.data.dto

data class EmployerResponse(
    val accredited_it_employer: Boolean,
    val alternate_url: String?,
    val id: String?,
    val logo_urls: LogoUrlsResponse?,
    val name: String,
    val trusted: Boolean,
    val url: String?,
    val vacancies_url: String?,
    val blacklisted: Boolean,
)
