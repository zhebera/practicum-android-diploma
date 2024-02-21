package ru.practicum.android.diploma.data.converters.db.employer

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.data.db.entity.EmployerEntity

object EmployerDbConverter {

    @TypeConverter
    fun fromEmployerEntity(data: String?): EmployerEntity? {
        return Gson().fromJson(data, EmployerEntity::class.java)
    }

    @TypeConverter
    fun toEmployerEntity(employer: EmployerEntity?): String {
        return Gson().toJson(employer)
    }
}
