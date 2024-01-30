package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.practicum.android.diploma.data.converters.*

@Entity(tableName = "vacancy_table")
@TypeConverters(VacancyDbConverter::class)
data class VacancyDescriptionEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val salary: SalaryEntity?,
    val employer: EmployerEntity?,
    val area: AreaEntity?,
    val address: AddressEntity?,
    val experience: ExperienceEntity?,
    val employment: EmploymentEntity?,
    val schedule: ScheduleEntity?,
    val keySkills: List<KeySkillEntity>,
    val contacts: ContactsEntity?,
    val description: String,
)

@TypeConverters(SalaryDbConverter::class)
data class SalaryEntity(
    val currency: String?,
    val from: Int?,
    val gross: Boolean?,
    val to: Int?
)

@TypeConverters(AreaDbConverter::class)
data class AreaEntity(
    val id: String,
    val name: String,
    val url: String
)

@TypeConverters(EmploymentDbConverter::class)
data class EmployerEntity(
    val alternateUrl: String?,
    val id: String?,
    val logoUrls: LogoUrlsEntity?,
    val name: String?,
    val url: String?
)

@TypeConverters(AddressDbConverter::class)
data class AddressEntity(
    val building: String?,
    val city: String?,
    val description: String?,
    val lat: Double?,
    val lng: Double?,
    val street: String?
)

@TypeConverters(ExperienceDbConverter::class)
data class ExperienceEntity(
    val id: String,
    val name: String
)

@TypeConverters(EmploymentDbConverter::class)
data class EmploymentEntity(
    val id: String,
    val name: String
)

@TypeConverters(ScheduleDbConverter::class)
data class ScheduleEntity(
    val id: String?,
    val name: String
)

@TypeConverters(KeySkillDbConverter::class)
data class KeySkillEntity(
    val name: String
)

@TypeConverters(ContactsDbConverter::class)
data class ContactsEntity(
    val email: String?,
    val name: String?,
    val phones: List<PhoneEntity>?
)

@TypeConverters(LogoUrlsDbConverter::class)
data class LogoUrlsEntity(
    val original: String
)

data class PhoneEntity(
    val city: String,
    val comment: Any?,
    val country: String,
    val number: String
)

