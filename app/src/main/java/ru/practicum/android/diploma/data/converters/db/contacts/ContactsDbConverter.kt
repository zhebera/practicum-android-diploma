package ru.practicum.android.diploma.data.converters.db.contacts

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.data.db.entity.ContactsEntity

object ContactsDbConverter {
    @TypeConverter
    fun fromContactsEntity(data: String): ContactsEntity? {
        return Gson().fromJson(data, ContactsEntity::class.java)
    }

    @TypeConverter
    fun toContactsEntity(contacts: ContactsEntity?): String {
        return Gson().toJson(contacts)
    }
}
