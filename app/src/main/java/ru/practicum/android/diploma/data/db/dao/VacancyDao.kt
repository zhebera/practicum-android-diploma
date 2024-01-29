package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import ru.practicum.android.diploma.data.db.entity.VacancyDescriptionEntity

@Dao
interface VacancyDao {
    @Query("SELECT * FROM vacancy_table")
    suspend fun getAllVacancies(): List<VacancyDescriptionEntity>
}
