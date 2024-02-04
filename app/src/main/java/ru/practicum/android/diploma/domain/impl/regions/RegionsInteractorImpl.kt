package ru.practicum.android.diploma.domain.impl.regions

import ru.practicum.android.diploma.domain.api.regions.RegionsInteractor
import ru.practicum.android.diploma.domain.api.regions.RegionsRepository

class RegionsInteractorImpl(private val regionsRepository: RegionsRepository): RegionsInteractor {

    override fun getRegions(areaId: String?) {
        regionsRepository.getRegions(areaId)
    }
}
