package ru.practicum.android.diploma.data.converters.db.experience

import ru.practicum.android.diploma.data.db.entity.ExperienceEntity
import ru.practicum.android.diploma.domain.models.Experience

object ExperienceEntityConverter {

    fun map(experienceEntity: ExperienceEntity?): Experience? {
        return if (experienceEntity != null) {
            Experience(
                experienceEntity.id,
                experienceEntity.name
            )
        } else null
    }

    fun map(experience: Experience?): ExperienceEntity? {
        return if (experience != null) {
            ExperienceEntity(
                experience.id,
                experience.name
            )
        } else null
    }
}
