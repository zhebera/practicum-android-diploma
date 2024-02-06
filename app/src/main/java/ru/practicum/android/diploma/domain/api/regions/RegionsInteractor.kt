package ru.practicum.android.diploma.domain.api.regions

import android.graphics.Region
import kotlinx.coroutines.flow.Flow

interface RegionsInteractor {

    fun getRegions(areaId: String? = null): Flow<Pair<List<Region>?, String?>>
}
