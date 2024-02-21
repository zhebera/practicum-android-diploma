package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
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
import ru.practicum.android.diploma.data.converters.db.phone.PhoneDbConverter
import ru.practicum.android.diploma.data.db.entity.VacancyDescriptionEntity
import ru.practicum.android.diploma.data.db.dao.VacancyDao

@Database(entities = [VacancyDescriptionEntity::class], version = 1)
@TypeConverters(
    VacancyDbConverter::class, ContactsDbConverter::class, AddressDbConverter::class,
    EmploymentDbConverter::class, EmployerDbConverter::class, ExperienceDbConverter::class,
    KeySkillDbConverter::class, ListKeySkillDbConverter::class, LogoUrlsDbConverter::class,
    SalaryDbConverter::class, ScheduleDbConverter::class, AreaDbConverter::class, PhoneDbConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun vacancyDao(): VacancyDao
}
