package ru.practicum.android.diploma.data.converters.db.address

import ru.practicum.android.diploma.data.db.entity.AddressEntity
import ru.practicum.android.diploma.domain.models.Address

object AddressEntityConverter {

    fun map(addressEntity: AddressEntity?): Address {
        return Address(
            addressEntity?.building,
            addressEntity?.city,
            addressEntity?.description,
            addressEntity?.lat,
            addressEntity?.lng,
            addressEntity?.street,
        )
    }

    fun map(address: Address?): AddressEntity {
        return AddressEntity(
            address?.building,
            address?.city,
            address?.description,
            address?.lat,
            address?.lng,
            address?.street,
        )
    }
}
