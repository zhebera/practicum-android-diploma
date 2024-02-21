package ru.practicum.android.diploma.data.converters.db.address

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.data.db.entity.AddressEntity

object AddressDbConverter {

    @TypeConverter
    fun fromAddressEntity(data: String?): AddressEntity? {
        return Gson().fromJson(data, AddressEntity::class.java)
    }

    @TypeConverter
    fun toAddressEntity(address: AddressEntity?): String {
        return Gson().toJson(address)
    }
}
