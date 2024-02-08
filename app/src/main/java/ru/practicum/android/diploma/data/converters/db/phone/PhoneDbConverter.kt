package ru.practicum.android.diploma.data.converters.db.phone

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.data.db.entity.PhoneEntity

object PhoneDbConverter {

    @TypeConverter
    fun fromPhoneEntity(data: String): PhoneEntity? {
        return Gson().fromJson(data, PhoneEntity::class.java)
    }

    @TypeConverter
    fun toPhoneEntity(phone: PhoneEntity?): String {
        return Gson().toJson(phone)
    }
}
