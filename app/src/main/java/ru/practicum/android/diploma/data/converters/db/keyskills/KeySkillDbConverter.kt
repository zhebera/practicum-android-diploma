package ru.practicum.android.diploma.data.converters.db.keyskills

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.data.db.entity.KeySkillEntity

object KeySkillDbConverter {

    @TypeConverter
    fun fromKeySkillEntity(data: String) = Gson().fromJson(data, KeySkillEntity::class.java)

    @TypeConverter
    fun toKeySkillEntity(keySkillEntity: KeySkillEntity?) = Gson().toJson(keySkillEntity)
}
