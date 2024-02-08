package ru.practicum.android.diploma.data.converters.db.schedule

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.data.db.entity.ScheduleEntity

object ScheduleDbConverter {

    @TypeConverter
    fun fromScheduleEntity(data: String): ScheduleEntity? {
        return Gson().fromJson(data, ScheduleEntity::class.java)
    }

    @TypeConverter
    fun toScheduleEntity(schedule: ScheduleEntity?): String {
        return Gson().toJson(schedule)
    }
}
