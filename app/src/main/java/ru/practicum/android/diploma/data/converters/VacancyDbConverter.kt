package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.db.entity.*
import ru.practicum.android.diploma.domain.models.*

class VacancyDbConverter {

    fun convert(vacancyEntity: VacancyDescriptionEntity): VacancyDescription {
        return VacancyDescription(
            id = vacancyEntity.id,
            name = vacancyEntity.name,
            salary = convertSalary(vacancyEntity.salary),
            employer = convertEmployer(vacancyEntity.employer),
            area = vacancyEntity.area,
            address = convertAddress(vacancyEntity.address),
            experience = convertExperience(vacancyEntity.experience),
            employment = convertEmployment(vacancyEntity.employment),
            schedule = convertSchedule(vacancyEntity.schedule),
            branded_description = vacancyEntity.brandedDescription,
            key_skills = convertKeySkills(vacancyEntity.keySkills),
            contacts = convertContacts(vacancyEntity.contacts),
            description = vacancyEntity.description
        )
    }

    fun convert(vacancy: VacancyDescription): VacancyDescriptionEntity {
        return VacancyDescriptionEntity(
            id = vacancy.id,
            name = vacancy.name,
            salary = convertSalary(vacancy.salary),
            employer = convertEmployer(vacancy.employer),
            area = vacancy.area,
            address = convertAddress(vacancy.address),
            experience = convertExperience(vacancy.experience),
            employment = convertEmployment(vacancy.employment),
            schedule = convertSchedule(vacancy.schedule),
            brandedDescription = vacancy.branded_description,
            keySkills = convertKeySkills(vacancy.key_skills),
            contacts = convertContacts(vacancy.contacts),
            description = vacancy.description
        )
    }

    fun convert(listVacancyEntity: List<VacancyDescriptionEntity>): List<VacancyDescription> {
        return listVacancyEntity.map { vacancyEntity ->
            convert(vacancyEntity)
        }
    }

    private fun convertSalary(salaryEntity: SalaryEntity): Salary {
        return Salary(
            salaryEntity.currency,
            salaryEntity.from,
            salaryEntity.gross,
            salaryEntity.to
        )
    }

    private fun convertSalary(salary: Salary): SalaryEntity {
        return SalaryEntity(
            salary.currency,
            salary.from,
            salary.gross,
            salary.to
        )
    }

    private fun convertEmployer(employerEntity: EmployerEntity): Employer {
        return Employer(
            employerEntity.alternateUrl,
            employerEntity.id,
            employerEntity.logoUrls,
            employerEntity.name,
            employerEntity.url
        )
    }

    private fun convertEmployer(employer: Employer): EmployerEntity {
        return EmployerEntity(
            employer.alternateUrl,
            employer.id,
            employer.logoUrls,
            employer.name,
            employer.url
        )
    }

    private fun convertAddress(addressEntity: AddressEntity): Address {
        return Address(
            addressEntity.building,
            addressEntity.city,
            addressEntity.description,
            addressEntity.lat,
            addressEntity.lng,
            addressEntity.street
        )
    }

    private fun convertAddress(address: Address): AddressEntity {
        return AddressEntity(
            address.building,
            address.city,
            address.description,
            address.lat,
            address.lng,
            address.street
        )
    }

    private fun convertExperience(experienceEntity: ExperienceEntity): Experience {
        return Experience(
            experienceEntity.id,
            experienceEntity.name
        )
    }

    private fun convertExperience(experience: Experience): ExperienceEntity {
        return ExperienceEntity(
            experience.id,
            experience.name
        )
    }

    private fun convertEmployment(employmentEntity: EmploymentEntity): Employment {
        return Employment(
            employmentEntity.id,
            employmentEntity.name
        )
    }

    private fun convertEmployment(employment: Employment): EmploymentEntity {
        return EmploymentEntity(
            employment.id,
            employment.name
        )
    }

    private fun convertSchedule(scheduleEntity: ScheduleEntity): Schedule {
        return Schedule(
            scheduleEntity.id,
            scheduleEntity.name
        )
    }

    private fun convertSchedule(schedule: Schedule): ScheduleEntity {
        return ScheduleEntity(
            schedule.id,
            schedule.name
        )
    }

    private fun convertKeySkills(listKeySkillsEntity: List<KeySkillEntity>): List<KeySkill> {
        return listKeySkillsEntity.map { keySkillEntity ->
            convertKeySkill(keySkillEntity)
        }
    }

    private fun convertKeySkills(listKeySkills: List<KeySkill>): List<KeySkillEntity> {
        return listKeySkills.map { keySkill ->
            convertKeySkill(keySkill)
        }
    }

    private fun convertKeySkill(keySkillEntity: KeySkillEntity) = KeySkill(keySkillEntity.name)

    private fun convertKeySkill(keySkill: KeySkill) = KeySkillEntity(keySkill.name)

    private fun convertContacts(contactsEntity: ContactsEntity): Contacts {
        return Contacts(
            contactsEntity.email,
            contactsEntity.name,
            contactsEntity.phones
        )
    }

    private fun convertContacts(contacts: Contacts): ContactsEntity {
        return ContactsEntity(
            contacts.email,
            contacts.name,
            contacts.phones
        )
    }

}
