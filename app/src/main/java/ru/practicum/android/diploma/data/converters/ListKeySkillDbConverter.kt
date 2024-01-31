package ru.practicum.android.diploma.data.converters

import androidx.room.TypeConverter
import ru.practicum.android.diploma.data.converters.KeySkillDbConverter.fromKeySkillEntity
import ru.practicum.android.diploma.data.converters.KeySkillDbConverter.toKeySkillEntity
import ru.practicum.android.diploma.domain.models.KeySkill
import java.util.stream.Collectors

object ListKeySkillDbConverter {

    @TypeConverter
    fun fromKeySkillsEntity(data: String): List<KeySkill> {
        val massive = data.split('^')
        val list = mutableListOf<KeySkill>()
        massive.forEach { str ->
            list.add(fromKeySkillEntity(str))
        }
        return list
    }

    @TypeConverter
    fun toKeySkillsEntity(listKeySkills: List<KeySkill>): String {
        val list = mutableListOf<String>()
        listKeySkills.forEach { keySkill ->
            list.add(toKeySkillEntity(keySkill))
        }
        return convertList(list)
    }

    @TypeConverter
    fun convertList(list: List<String>): String =
        list.stream().collect(Collectors.joining("^"))
}
