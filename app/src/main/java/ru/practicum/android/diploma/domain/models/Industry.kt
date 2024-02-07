package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Industry(
    val id: String? = null,
    val name: String? = null,
    val isChecked: Boolean = false
) : Parcelable
