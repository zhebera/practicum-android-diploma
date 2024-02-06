package ru.practicum.android.diploma.domain.models

data class FilterModel(
    val countryName: String? = null,
    val countryId: String? = null,
    val regionName: String? = null,
    val regionId: String? = null,
    val industryName: String? = null,
    val industryId: String? = null,
    val salary: String? = null,
    val onlyWithSalary: Boolean? = null
)
