package ru.practicum.android.diploma.data.converters.db.contacts

import ru.practicum.android.diploma.data.converters.db.phone.PhoneEntityConverter
import ru.practicum.android.diploma.data.db.entity.ContactsEntity
import ru.practicum.android.diploma.domain.models.Contacts

object ContactsEntityConverter {

    fun map(contactsEntity: ContactsEntity?): Contacts {
        return Contacts(
            contactsEntity?.email,
            contactsEntity?.name,
            PhoneEntityConverter.mapToModel(contactsEntity?.phones)
        )
    }

    fun map(contacts: Contacts?): ContactsEntity {
        return ContactsEntity(
            contacts?.email,
            contacts?.name,
            PhoneEntityConverter.mapToEntity(contacts?.phones)
        )
    }
}
