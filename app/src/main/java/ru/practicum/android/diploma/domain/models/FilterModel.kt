package ru.practicum.android.diploma.domain.models

data class FilterModel(
    val country: Country? = null,
    val region: Region? = null,
    val industry: Industry? = null,
    val salary: String? = null,
    val onlyWithSalary: Boolean? = null
)
