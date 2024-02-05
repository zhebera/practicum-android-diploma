package ru.practicum.android.diploma.domain.api.regions

interface RegionsRepository {

    fun getRegions(areaId: String? = null)
}
