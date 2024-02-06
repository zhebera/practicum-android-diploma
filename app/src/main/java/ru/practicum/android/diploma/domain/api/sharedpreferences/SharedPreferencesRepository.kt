package ru.practicum.android.diploma.domain.api.sharedpreferences

import ru.practicum.android.diploma.domain.models.FilterModel

interface SharedPreferencesRepository {
    fun getFilter(): FilterModel
    fun setCountry(name: String, id: String)
    fun removeCountry()
    fun setRegion(name: String, id: String)
    fun removeRegion()
    fun setIndustry(name: String, id: String)
    fun removeIndustry()
    fun setSalary(input: String)
    fun setSalaryCheckbox(input: Boolean)
    fun doesFilterApplied(): Boolean
}
