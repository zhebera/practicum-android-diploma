package ru.practicum.android.diploma.data.db.dao

import androidx.room.*
import ru.practicum.android.diploma.data.converters.*
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
}
