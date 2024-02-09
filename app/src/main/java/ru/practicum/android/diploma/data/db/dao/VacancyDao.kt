package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import ru.practicum.android.diploma.data.converters.db.address.AddressDbConverter
import ru.practicum.android.diploma.data.converters.db.area.AreaDbConverter
import ru.practicum.android.diploma.data.converters.db.contacts.ContactsDbConverter
import ru.practicum.android.diploma.data.converters.db.employer.EmployerDbConverter
import ru.practicum.android.diploma.data.converters.db.employment.EmploymentDbConverter
import ru.practicum.android.diploma.data.converters.db.experience.ExperienceDbConverter
import ru.practicum.android.diploma.data.converters.db.keyskills.KeySkillDbConverter
import ru.practicum.android.diploma.data.converters.db.keyskills.ListKeySkillDbConverter
import ru.practicum.android.diploma.data.converters.db.logo.LogoUrlsDbConverter
import ru.practicum.android.diploma.data.converters.db.salary.SalaryDbConverter
import ru.practicum.android.diploma.data.converters.db.schedule.ScheduleDbConverter
import ru.practicum.android.diploma.data.converters.db.VacancyDbConverter
import ru.practicum.android.diploma.data.db.entity.VacancyDescriptionEntity

@Dao
@TypeConverters(
    VacancyDbConverter::class, ContactsDbConverter::class, AddressDbConverter::class,
    EmploymentDbConverter::class, EmployerDbConverter::class, ExperienceDbConverter::class,
    KeySkillDbConverter::class, ListKeySkillDbConverter::class, LogoUrlsDbConverter::class,
    SalaryDbConverter::class, ScheduleDbConverter::class, AreaDbConverter::class
)
interface VacancyDao {
    @Query("SELECT * FROM vacancy_table")
    suspend fun getAllVacancies(): List<VacancyDescriptionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addVacancy(vacancyDescriptionEntity: VacancyDescriptionEntity)

    @Query("DELETE FROM vacancy_table WHERE id = :vacancyId")
    suspend fun removeVacancy(vacancyId: String)

    @Query("SELECT * FROM vacancy_table WHERE id = :vacancyId")
    suspend fun getVacancyById(vacancyId: String): VacancyDescriptionEntity?
}
