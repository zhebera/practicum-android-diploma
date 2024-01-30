package ru.practicum.android.diploma.data.converters

import androidx.room.TypeConverter
import ru.practicum.android.diploma.data.converters.KeySkillDbConverter.fromKeySkillEntity
import ru.practicum.android.diploma.data.converters.KeySkillDbConverter.toKeySkillEntity
import ru.practicum.android.diploma.domain.models.KeySkill
import java.lang.StringBuilder

object ListKeySkillDbConverter {

    @TypeConverter
    fun fromKeySkillsEntity(data: String): List<KeySkill> {
        val massive = data.split(";")
        val list = mutableListOf<KeySkill>()
        massive.forEach { str ->
            list.add(fromKeySkillEntity(str))
        }
        return list.toList()
    }

    @TypeConverter
    fun toKeySkillsEntity(listKeySkills: List<KeySkill>): String {
        val resultString = StringBuilder()
        listKeySkills.forEach { keySkill ->
            resultString.append(toKeySkillEntity(keySkill)).trim(';')
        }
        return resultString.toString()
    }
}
