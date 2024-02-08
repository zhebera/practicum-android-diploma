package ru.practicum.android.diploma.data.converters.db.employer

import ru.practicum.android.diploma.data.converters.db.logo.LogoUrlsEntityConverter
import ru.practicum.android.diploma.data.db.entity.EmployerEntity
import ru.practicum.android.diploma.domain.models.Employer

object EmployerEntityConverter {

    fun map(employerEntity: EmployerEntity?): Employer {
        return Employer(
            employerEntity?.alternateUrl,
            employerEntity?.id,
            LogoUrlsEntityConverter.map(employerEntity?.logoUrls),
            employerEntity?.name,
            employerEntity?.url
        )
    }

    fun map(employer: Employer?): EmployerEntity {
        return EmployerEntity(
            employer?.alternateUrl,
            employer?.id,
            LogoUrlsEntityConverter.map(employer?.logoUrls),
            employer?.name,
            employer?.url
        )
    }
}
