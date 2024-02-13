package ru.practicum.android.diploma.domain.api.sharedpreferences

import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.FilterModel
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.Region

interface SharedPreferencesRepository {
    fun getFilter(): FilterModel?
    fun saveFilter(country: Country?, region: Region?, industry: Industry?, salary: String?, onlyWithSalary: Boolean?)
    fun clearFilter()
}
