package ru.practicum.android.diploma.domain.api.sharedpreferences

import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.FilterModel
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.Region

interface SharedPreferencesInteractor {
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
