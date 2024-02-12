package ru.practicum.android.diploma.data.converters.db.phone

import ru.practicum.android.diploma.data.db.entity.PhoneEntity
import ru.practicum.android.diploma.domain.models.Phone

object PhoneEntityConverter {

    fun map(phoneEntity: PhoneEntity): Phone {
        return Phone(
            phoneEntity.city,
            phoneEntity.comment,
            phoneEntity.country,
            phoneEntity.number
        )
    }

    fun map(phone: Phone): PhoneEntity {
        return PhoneEntity(
            phone.city,
            phone.comment,
            phone.country,
            phone.number
        )
    }

    fun mapToModel(listPhoneEntity: List<PhoneEntity>?): List<Phone>? {
        return listPhoneEntity?.map {
            map(it)
        }
    }

    fun mapToEntity(listPhone: List<Phone>?): List<PhoneEntity>? {
        return listPhone?.map {
            map(it)
        }
    }
}
