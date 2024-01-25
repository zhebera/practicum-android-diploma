package ru.practicum.android.diploma.domain.models

data class Vacancy(
    val id: String,
    val name: String,
    val area: Area,
    val address: Address?,
    val employer: Employer,
    val department: Department?,
    val salary: Salary?
)
