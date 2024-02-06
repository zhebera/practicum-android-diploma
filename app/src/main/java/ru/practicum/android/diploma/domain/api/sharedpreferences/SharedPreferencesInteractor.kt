package ru.practicum.android.diploma.domain.api.sharedpreferences

import ru.practicum.android.diploma.domain.models.FilterModel

interface SharedPreferencesInteractor {
    fun getFilter(): FilterModel
    fun setCountry(name: String, id: String)
    fun setRegion(name: String, id: String)
    fun setIndustry(name: String, id: String)
    fun setSalary(input: String)
    fun setSalaryCheckbox(input: Boolean)
    fun doesFilterApplied(): Boolean
}
