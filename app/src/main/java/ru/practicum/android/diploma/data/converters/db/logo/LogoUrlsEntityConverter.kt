package ru.practicum.android.diploma.data.converters.db.logo

import ru.practicum.android.diploma.data.db.entity.LogoUrlsEntity
import ru.practicum.android.diploma.domain.models.LogoUrls

object LogoUrlsEntityConverter {

    fun map(logoUrls: LogoUrls?): LogoUrlsEntity? {
        return if (logoUrls != null) {
            LogoUrlsEntity(
                logoUrls.original
            )
        } else null
    }

    fun map(logoUrlsEntity: LogoUrlsEntity?): LogoUrls? {
        return if (logoUrlsEntity != null) {
            LogoUrls(
                logoUrlsEntity.original
            )
        } else null
    }
}
