package ru.practicum.android.diploma.data.response

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.Response

data class RegionResponse(
    val areas: List<RegionResponse>,
    val id: String?,
    val name: String?,
    @SerializedName("parent_id") val parentId: String? = null
) : Response()
