package ru.practicum.android.diploma.data.converters.db.keyskills

import androidx.room.TypeConverter
import ru.practicum.android.diploma.data.converters.db.keyskills.KeySkillDbConverter.fromKeySkillEntity
import ru.practicum.android.diploma.data.converters.db.keyskills.KeySkillDbConverter.toKeySkillEntity
import ru.practicum.android.diploma.data.db.entity.KeySkillEntity
import java.util.stream.Collectors

object ListKeySkillDbConverter {

    @TypeConverter
    fun fromKeySkillsEntity(data: String): List<KeySkillEntity>? {
        if (data.equals("null")) return null
        val massive = data.split('^')
        val list = mutableListOf<KeySkillEntity>()
        massive.forEach { str ->
            list.add(fromKeySkillEntity(str))
        }
        return list
    }

    @TypeConverter
    fun toKeySkillsEntity(listKeySkills: List<KeySkillEntity>?): String {
        val list = mutableListOf<String>()
        if (listKeySkills.isNullOrEmpty()) {
            list.add(toKeySkillEntity(null))
        } else {
            listKeySkills.forEach { keySkillEntity ->
                list.add(toKeySkillEntity(keySkillEntity))
            }
        }
        return convertList(list)
    }

    @TypeConverter
    fun convertList(list: List<String>): String =
        list.stream().collect(Collectors.joining("^"))
}
