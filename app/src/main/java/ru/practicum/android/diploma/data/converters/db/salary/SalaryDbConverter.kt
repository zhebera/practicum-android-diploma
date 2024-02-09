package ru.practicum.android.diploma.data.converters.db.salary

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.data.db.entity.SalaryEntity

object SalaryDbConverter {
    @TypeConverter
    fun fromSalaryEntity(data: String?): SalaryEntity? {
        return Gson().fromJson(data, SalaryEntity::class.java)
    }

    @TypeConverter
    fun toSalaryEntity(salary: SalaryEntity?): String {
        return Gson().toJson(salary)
    }
}
