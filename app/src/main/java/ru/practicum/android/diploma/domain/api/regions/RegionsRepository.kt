package ru.practicum.android.diploma.domain.api.regions

import android.graphics.Region
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.util.Resource

interface RegionsRepository {

    fun getRegions(areaId: String? = null) : Flow<Resource<List<Region>>>
}
