package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Employer(
    val alternateUrl: String?,
    val id: String?,
    val logoUrls: LogoUrls?,
    val name: String?,
    val url: String?
) : Parcelable

data class EmployerDescription(
    val alternateUrl: String?,
    val blacklisted: Boolean?,
    val id: String?,
    val logoUrls: LogoUrls?,
    val name: String,
    val trusted: Boolean,
    val url: String?
)
