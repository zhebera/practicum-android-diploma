package ru.practicum.android.diploma.data.converters.db

import androidx.room.TypeConverter
import ru.practicum.android.diploma.data.converters.db.address.AddressEntityConverter
import ru.practicum.android.diploma.data.converters.db.area.AreaEntityConverter
import ru.practicum.android.diploma.data.converters.db.contacts.ContactsEntityConverter
import ru.practicum.android.diploma.data.converters.db.employer.EmployerEntityConverter
import ru.practicum.android.diploma.data.converters.db.employment.EmploymentEntityConverter
import ru.practicum.android.diploma.data.converters.db.experience.ExperienceEntityConverter
import ru.practicum.android.diploma.data.converters.db.keyskills.KeySkillEntityConverter
import ru.practicum.android.diploma.data.converters.db.salary.SalaryEntityConverter
import ru.practicum.android.diploma.data.converters.db.schedule.ScheduleEntityConveter
import ru.practicum.android.diploma.data.db.entity.VacancyDescriptionEntity
import ru.practicum.android.diploma.domain.models.VacancyDescription

class VacancyDbConverter {
    @TypeConverter
    fun convertFromEntity(vacancyEntity: VacancyDescriptionEntity): VacancyDescription {
        return VacancyDescription(
            id = vacancyEntity.id,
            name = vacancyEntity.name,
            salary = SalaryEntityConverter.map(vacancyEntity.salary),
            employer = EmployerEntityConverter.map(vacancyEntity.employer),
            area = AreaEntityConverter.map(vacancyEntity.area),
            url = vacancyEntity.url,
            address = AddressEntityConverter.map(vacancyEntity.address),
            experience = ExperienceEntityConverter.map(vacancyEntity.experience),
            employment = EmploymentEntityConverter.map(vacancyEntity.employment),
            schedule = ScheduleEntityConveter.map(vacancyEntity.schedule),
            keySkills = KeySkillEntityConverter.mapToModel(vacancyEntity.keySkills),
            contacts = ContactsEntityConverter.map(vacancyEntity.contacts),
            description = vacancyEntity.description
        )
    }

    @TypeConverter
    fun convertToEntity(vacancy: VacancyDescription): VacancyDescriptionEntity {
        return VacancyDescriptionEntity(
            id = vacancy.id,
            name = vacancy.name,
            salary = SalaryEntityConverter.map(vacancy.salary),
            employer = EmployerEntityConverter.map(vacancy.employer),
            area = AreaEntityConverter.map(vacancy.area),
            url = vacancy.url,
            address = AddressEntityConverter.map(vacancy.address),
            experience = ExperienceEntityConverter.map(vacancy.experience),
            employment = EmploymentEntityConverter.map(vacancy.employment),
            schedule = ScheduleEntityConveter.map(vacancy.schedule),
            keySkills = KeySkillEntityConverter.mapToEntity(vacancy.keySkills),
            contacts = ContactsEntityConverter.map(vacancy.contacts),
            description = vacancy.description
        )
    }

    @TypeConverter
    fun convert(listVacancyEntity: List<VacancyDescriptionEntity>): List<VacancyDescription> {
        return listVacancyEntity.map { vacancyEntity ->
            convertFromEntity(vacancyEntity)
        }
    }
}
