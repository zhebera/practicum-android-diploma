package ru.practicum.android.diploma.data.converters.db.experience

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.data.db.entity.ExperienceEntity

object ExperienceDbConverter {

    @TypeConverter
    fun fromExperienceEntity(data: String): ExperienceEntity? {
        return Gson().fromJson(data, ExperienceEntity::class.java)
    }

    @TypeConverter
    fun toExperienceEntity(experience: ExperienceEntity?): String {
        return Gson().toJson(experience)
    }
}
