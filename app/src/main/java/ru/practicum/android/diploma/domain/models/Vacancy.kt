package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Vacancy(
    val id: String,
    val name: String,
    val area: Area,
    val employer: Employer,
    val department: Department?,
    val salary: Salary?
) : Parcelable
