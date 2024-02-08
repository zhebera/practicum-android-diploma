package ru.practicum.android.diploma.data.converters.db.area

import ru.practicum.android.diploma.data.db.entity.AreaEntity
import ru.practicum.android.diploma.domain.models.Area

object AreaEntityConverter {

    fun map(areaEntity: AreaEntity): Area {
        return Area(
            areaEntity.id,
            areaEntity.name,
            areaEntity.url
        )
    }

    fun map(area: Area): AreaEntity {
        return AreaEntity(
            area.id,
            area.name,
            area.url
        )
    }
}
