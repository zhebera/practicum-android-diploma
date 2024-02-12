package ru.practicum.android.diploma.data.converters.db.keyskills

import ru.practicum.android.diploma.data.db.entity.KeySkillEntity
import ru.practicum.android.diploma.domain.models.KeySkill

object KeySkillEntityConverter {

    fun map(keySkillEntity: KeySkillEntity): KeySkill {
        return KeySkill(
            keySkillEntity.name
        )
    }

    fun map(keySkill: KeySkill): KeySkillEntity {
        return KeySkillEntity(
            keySkill.name
        )
    }

    fun mapToEntity(listKeySkill: List<KeySkill>): List<KeySkillEntity> {
        return listKeySkill.map { map(it) }
    }

    fun mapToModel(listKeySkillEntity: List<KeySkillEntity>): List<KeySkill> {
        return listKeySkillEntity.map { map(it) }
    }
}
