package ru.practicum.android.diploma.data.converters.db.employment

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.data.db.entity.EmploymentEntity

object EmploymentDbConverter {

    @TypeConverter
    fun fromEmploymentEntity(data: String?): EmploymentEntity? {
        return Gson().fromJson(data, EmploymentEntity::class.java)
    }

    @TypeConverter
    fun toEmploymentEntity(employment: EmploymentEntity?): String {
        return Gson().toJson(employment)
    }
}
