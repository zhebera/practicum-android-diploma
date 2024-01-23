package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity

@Entity(tableName = "vacancy_table")
data class VacancyEntity(
    val id: String,
    val name: String
)