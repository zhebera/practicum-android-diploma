package ru.practicum.android.diploma.data.converters

import androidx.room.TypeConverter
import ru.practicum.android.diploma.data.db.entity.*
import ru.practicum.android.diploma.domain.models.*

class VacancyDbConverter {

    @TypeConverter
    fun convert(vacancyEntity: VacancyDescriptionEntity): VacancyDescription {
        return VacancyDescription(
            id = vacancyEntity.id,
            name = vacancyEntity.name,
            salary = convertSalary(vacancyEntity.salary),
            employer = convertEmployer(vacancyEntity.employer),
            area = convertArea(vacancyEntity.area),
            address = convertAddress(vacancyEntity.address),
            experience = convertExperience(vacancyEntity.experience),
            employment = convertEmployment(vacancyEntity.employment),
            schedule = convertSchedule(vacancyEntity.schedule),
            keySkills = convertKeySkillsFromEntity(vacancyEntity.keySkills),
            contacts = convertContacts(vacancyEntity.contacts),
            description = vacancyEntity.description
        )
    }

    @TypeConverter
    fun convert(vacancy: VacancyDescription): VacancyDescriptionEntity {
        return VacancyDescriptionEntity(
            id = vacancy.id,
            name = vacancy.name,
            salary = convertSalary(vacancy.salary),
            employer = convertEmployer(vacancy.employer),
            area = convertArea(vacancy.area),
            address = convertAddress(vacancy.address),
            experience = convertExperience(vacancy.experience),
            employment = convertEmployment(vacancy.employment),
            schedule = convertSchedule(vacancy.schedule),
            keySkills = convertKeySkillsToEntity(vacancy.keySkills),
            contacts = convertContacts(vacancy.contacts),
            description = vacancy.description
        )
    }

    @TypeConverter
    fun convert(listVacancyEntity: List<VacancyDescriptionEntity>): List<VacancyDescription> {
        return listVacancyEntity.map { vacancyEntity ->
            convert(vacancyEntity)
        }
    }

    @TypeConverter
    fun convertSalary(salaryEntity: SalaryEntity?): Salary? {
        return Salary(
            salaryEntity?.currency,
            salaryEntity?.from,
            salaryEntity?.gross,
            salaryEntity?.to
        )
    }

    @TypeConverter
    fun convertSalary(salary: Salary?): SalaryEntity? {
        return SalaryEntity(
            salary?.currency,
            salary?.from,
            salary?.gross,
            salary?.to
        )
    }

    @TypeConverter
    fun convertArea(areaEntity: AreaEntity?): Area? {
        return if (areaEntity != null) {
            Area(
                areaEntity.id,
                areaEntity.name,
                areaEntity.url
            )
        } else {
            null
        }
    }

    @TypeConverter
    fun convertArea(area: Area?): AreaEntity? {
        return if (area != null) {
            AreaEntity(
                area.id,
                area.name,
                area.url
            )
        } else {
            null
        }
    }

    @TypeConverter
    fun convertEmployer(employerEntity: EmployerEntity?): Employer? {
        return Employer(
            employerEntity?.alternateUrl,
            employerEntity?.id,
            convertLogoUrls(employerEntity?.logoUrls),
            employerEntity?.name,
            employerEntity?.url
        )
    }

    @TypeConverter
    fun convertEmployer(employer: Employer?): EmployerEntity? {
        return EmployerEntity(
            employer?.alternateUrl,
            employer?.id,
            convertLogoUrls(employer?.logoUrls),
            employer?.name,
            employer?.url
        )
    }

    @TypeConverter
    fun convertLogoUrls(logoUrls: LogoUrls?): LogoUrlsEntity? {
        return logoUrls?.original?.let {
            LogoUrlsEntity(
                it
            )
        }
    }

    @TypeConverter
    fun convertLogoUrls(logoUrlsEntity: LogoUrlsEntity?): LogoUrls? {
        return logoUrlsEntity?.let {
            LogoUrls(
                it.original
            )
        }
    }

    @TypeConverter
    fun convertAddress(addressEntity: AddressEntity?): Address? {
        return Address(
            addressEntity?.building,
            addressEntity?.city,
            addressEntity?.description,
            addressEntity?.lat,
            addressEntity?.lng,
            addressEntity?.street
        )
    }

    @TypeConverter
    fun convertAddress(address: Address?): AddressEntity? {
        return AddressEntity(
            address?.building,
            address?.city,
            address?.description,
            address?.lat,
            address?.lng,
            address?.street
        )
    }

    @TypeConverter
    fun convertExperience(experienceEntity: ExperienceEntity?): Experience? {
        return if (experienceEntity != null) {
            Experience(
                experienceEntity.id,
                experienceEntity.name
            )
        } else {
            null
        }
    }

    @TypeConverter
    fun convertExperience(experience: Experience?): ExperienceEntity? {
        return if (experience != null) {
            ExperienceEntity(
                experience.id,
                experience.name
            )
        } else {
            null
        }
    }

    @TypeConverter
    fun convertEmployment(employmentEntity: EmploymentEntity?): Employment? {
        return if (employmentEntity != null) {
            return Employment(
                employmentEntity.id,
                employmentEntity.name
            )
        } else {
            null
        }
    }

    @TypeConverter
    fun convertEmployment(employment: Employment?): EmploymentEntity? {
        return if (employment != null) {
            EmploymentEntity(
                employment.id,
                employment.name
            )
        } else {
            null
        }
    }

    @TypeConverter
    fun convertSchedule(scheduleEntity: ScheduleEntity?): Schedule? {
        return if (scheduleEntity != null) {
            Schedule(
                scheduleEntity.id,
                scheduleEntity.name
            )
        } else {
            null
        }
    }

    @TypeConverter
    fun convertSchedule(schedule: Schedule?): ScheduleEntity? {
        return if (schedule != null) {
            ScheduleEntity(
                schedule.id,
                schedule.name
            )
        } else {
            null
        }
    }

    @TypeConverter
    fun convertKeySkillsFromEntity(listKeySkillsEntity: List<KeySkillEntity>): List<KeySkill> {
        return listKeySkillsEntity.map { keySkillEntity ->
            convertKeySkill(keySkillEntity)
        }
    }

    @TypeConverter
    fun convertKeySkillsToEntity(listKeySkills: List<KeySkill>): List<KeySkillEntity> {
        return listKeySkills.map { keySkill ->
            convertKeySkill(keySkill)
        }
    }

    @TypeConverter
    fun convertKeySkill(keySkillEntity: KeySkillEntity) = KeySkill(keySkillEntity.name)

    @TypeConverter
    fun convertKeySkill(keySkill: KeySkill) = KeySkillEntity(keySkill.name)

    @TypeConverter
    fun convertContacts(contactsEntity: ContactsEntity?): Contacts? {
        return Contacts(
            contactsEntity?.email,
            contactsEntity?.name,
            contactsEntity?.phones?.map { phoneEntity ->
                convertPhone(phoneEntity)
            }
        )
    }

    @TypeConverter
    fun convertContacts(contacts: Contacts?): ContactsEntity? {
        return ContactsEntity(
            contacts?.email,
            contacts?.name,
            contacts?.phones?.map { phone ->
                convertPhone(phone)
            }
        )
    }

    @TypeConverter
    fun convertPhone(phone: Phone): PhoneEntity {
        return PhoneEntity(
            phone.city,
            phone.comment,
            phone.country,
            phone.number
        )
    }

    @TypeConverter
    fun convertPhone(phoneEntity: PhoneEntity): Phone {
        return Phone(
            phoneEntity.city,
            phoneEntity.comment,
            phoneEntity.country,
            phoneEntity.number
        )
    }
}
