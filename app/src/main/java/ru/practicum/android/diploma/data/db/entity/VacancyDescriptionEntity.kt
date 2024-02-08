package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.practicum.android.diploma.data.converters.db.VacancyDbConverter

@Entity(tableName = "vacancy_table")
@TypeConverters(VacancyDbConverter::class)
data class VacancyDescriptionEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val salary: SalaryEntity?,
    val employer: EmployerEntity?,
    val area: AreaEntity,
    val url: String,
    val address: AddressEntity?,
    val experience: ExperienceEntity?,
    val employment: EmploymentEntity?,
    val schedule: ScheduleEntity?,
    val keySkills: List<KeySkillEntity>,
    val contacts: ContactsEntity?,
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
    val logoUrls: LogoUrlsEntity?,
    val name: String?,
    val url: String?
)

data class LogoUrlsEntity(
    val original: String
)

data class AreaEntity(
    val id: String,
    val name: String,
    val url: String
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
    val id: String?,
    val name: String
)

data class KeySkillEntity(
    val name: String
)

data class ContactsEntity(
    val email: String?,
    val name: String?,
    val phones: List<PhoneEntity>?
)

data class PhoneEntity(
    val city: String,
    val comment: String?,
    val country: String,
    val number: String
)
