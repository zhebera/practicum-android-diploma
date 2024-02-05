package ru.practicum.android.diploma.domain.impl.regions

import android.graphics.Region
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.api.regions.RegionsInteractor
import ru.practicum.android.diploma.domain.api.regions.RegionsRepository
import ru.practicum.android.diploma.util.Resource

class RegionsInteractorImpl(private val regionsRepository: RegionsRepository) : RegionsInteractor {

    override fun getRegions(areaId: String?) : Flow<Pair<List<Region>?, String?>> {
        return regionsRepository.getRegions(areaId).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }
}
