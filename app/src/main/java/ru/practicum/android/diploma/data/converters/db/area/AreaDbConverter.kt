package ru.practicum.android.diploma.data.converters.db.area

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.data.db.entity.AreaEntity

object AreaDbConverter {

    @TypeConverter
    fun fromAreaEntity(data: String): AreaEntity {
        return Gson().fromJson(data, AreaEntity::class.java)
    }

    @TypeConverter
    fun toAreaEntity(area: AreaEntity): String {
        return Gson().toJson(area)
    }
}
