package ru.practicum.android.diploma.domain.api.regions

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Region

interface RegionsInteractor {

    fun getRegions(areaId: String? = null): Flow<Pair<List<Region>?, String?>>
}
