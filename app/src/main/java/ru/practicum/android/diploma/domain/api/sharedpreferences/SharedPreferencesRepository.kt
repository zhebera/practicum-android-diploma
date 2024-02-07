package ru.practicum.android.diploma.domain.api.sharedpreferences

import ru.practicum.android.diploma.domain.models.FilterModel

interface SharedPreferencesRepository {
    fun getFilter(): FilterModel?
    fun saveFilter(countryName: String?,
                   countryId: String?,
                   regionName: String?,
                   regionId: String?,
                   industryName: String?,
                   industryId: String?,
                   salary: String?,
                   onlyWithSalary: Boolean?)
    fun clearFilter()
}
