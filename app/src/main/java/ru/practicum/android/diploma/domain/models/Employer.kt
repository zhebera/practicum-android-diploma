package ru.practicum.android.diploma.domain.models

import ru.practicum.android.diploma.data.dto.LogoUrlsResponse

data class Employer(
    val alternate_url: String?,
    val blacklisted: Boolean?,
    val id: String?,
    val logo_urls: LogoUrls?,
    val name: String,
    val trusted: Boolean,
    val url: String?
)
