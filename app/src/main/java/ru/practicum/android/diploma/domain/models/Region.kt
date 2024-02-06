package ru.practicum.android.diploma.domain.models

data class Region(
    val includedRegions: List<Region>,
    val id: String?,
    val name: String?,
    val parentId: String? = null
)
