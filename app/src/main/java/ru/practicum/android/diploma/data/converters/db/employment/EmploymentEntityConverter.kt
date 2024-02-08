package ru.practicum.android.diploma.data.converters.db.employment

import ru.practicum.android.diploma.data.db.entity.EmploymentEntity
import ru.practicum.android.diploma.domain.models.Employment

object EmploymentEntityConverter {

    fun map(employmentEntity: EmploymentEntity?): Employment? {
        return if (employmentEntity != null) {
            Employment(
                employmentEntity.id,
                employmentEntity.name
            )
        } else {
            null
        }
    }

    fun map(employment: Employment?): EmploymentEntity? {
        return if (employment != null) {
            EmploymentEntity(
                employment.id,
                employment.name
            )
        } else {
            null
        }
    }
}
