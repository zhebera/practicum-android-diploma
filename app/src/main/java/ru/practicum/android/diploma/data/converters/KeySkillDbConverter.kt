package ru.practicum.android.diploma.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.models.KeySkill

object KeySkillDbConverter {

    @TypeConverter
    fun fromKeySkillEntity(data: String) = Gson().fromJson(data, KeySkill::class.java)

    @TypeConverter
    fun toKeySkillEntity(keySkill: KeySkill) = Gson().toJson(keySkill)
}
