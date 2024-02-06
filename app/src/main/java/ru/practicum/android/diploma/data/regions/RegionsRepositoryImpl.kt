package ru.practicum.android.diploma.data.regions

import android.graphics.Region
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.domain.api.regions.RegionsRepository
import ru.practicum.android.diploma.util.Resource

class RegionsRepositoryImpl : RegionsRepository {
    override fun getRegions(areaId: String?): Flow<Resource<List<Region>>> = flow {
        emit(Resource.Error(message = "badConnection"))
    }
}
