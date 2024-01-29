package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.practicum.android.diploma.domain.models.*

@Entity(tableName = "vacancy_table")
data class VacancyDescriptionEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val salary: SalaryEntity,
    val employer: EmployerEntity,
    val area: Area,
    val address: AddressEntity,
    val experience: ExperienceEntity,
    val employment: EmploymentEntity,
    val schedule: ScheduleEntity,
    val brandedDescription: String,
    val keySkills: List<KeySkillEntity>,
    val contacts: ContactsEntity,
    val description: String,
)

data class SalaryEntity(
    val currency: String?,
    val from: Int?,
    val gross: Boolean?,
    val to: Int?
)

data class EmployerEntity(
    val alternateUrl: String?,
    val id: String?,
    val logoUrls: LogoUrls?,
    val name: String?,
    val url: String?
)

data class AddressEntity(
    val building: String?,
    val city: String?,
    val description: String?,
    val lat: Double?,
    val lng: Double?,
    val street: String?
)

data class ExperienceEntity(
    val id: String,
    val name: String
)

data class EmploymentEntity(
    val id: String,
    val name: String
)

data class ScheduleEntity(
    val id: String,
    val name: String
)

data class KeySkillEntity(
    val name: String
)

data class ContactsEntity(
    val email: String,
    val name: String,
    val phones: List<Phone>
)
