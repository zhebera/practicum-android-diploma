package ru.practicum.android.diploma.data.sharedpreferences

import com.google.gson.Gson
import android.content.Context
import ru.practicum.android.diploma.domain.api.sharedpreferences.SharedPreferencesRepository
import ru.practicum.android.diploma.domain.models.FilterModel
import ru.practicum.android.diploma.util.FILTER_OBJECT_KEY
import ru.practicum.android.diploma.util.CAREER_KEY_APP_PREFERENCES

class SharedPreferencesRepositoryImpl(
    context: Context
) : SharedPreferencesRepository {
    private val androidContext = context
    private var gson = Gson()

    private val sharedPreferences = androidContext.getSharedPreferences(
        CAREER_KEY_APP_PREFERENCES,
        Context.MODE_PRIVATE
    )

    override fun getFilter(): FilterModel? {
        val json = sharedPreferences.getString(FILTER_OBJECT_KEY, "{}")

        return if (doesFilterApplied()) {
            gson.fromJson(json, FilterModel::class.java)
        } else {
            null
        }
    }

    override fun setCountry(name: String, id: String) {
        val filter = getFilterFromSharedPreferences()
        val updatedFilter = filter.copy(countryName = name, countryId = id)

        saveFilterToSharedPreferences(updatedFilter)
    }

    override fun removeCountry() {
        val filter = getFilterFromSharedPreferences()
        val updatedFilter = filter.copy(countryName = null, countryId = null)

        saveFilterToSharedPreferences(updatedFilter)
    }

    override fun setRegion(name: String, id: String) {
        val filter = getFilterFromSharedPreferences()
        val updatedFilter = filter.copy(regionName = name, regionId = id)

        saveFilterToSharedPreferences(updatedFilter)
    }

    override fun removeRegion() {
        val filter = getFilterFromSharedPreferences()
        val updatedFilter = filter.copy(regionName = null, regionId = null)

        saveFilterToSharedPreferences(updatedFilter)
    }

    override fun setIndustry(name: String, id: String) {
        val filter = getFilterFromSharedPreferences()
        val updatedFilter = filter.copy(industryName = name, industryId = id)

        saveFilterToSharedPreferences(updatedFilter)
    }

    override fun removeIndustry() {
        val filter = getFilterFromSharedPreferences()
        val updatedFilter = filter.copy(industryName = null, industryId = null)

        saveFilterToSharedPreferences(updatedFilter)
    }

    override fun setSalary(input: String) {
        val filter = getFilterFromSharedPreferences()
        val updatedFilter = filter.copy(salary = input)

        saveFilterToSharedPreferences(updatedFilter)
    }

    override fun setSalaryCheckbox(input: Boolean) {
        val filter = getFilterFromSharedPreferences()
        val updatedFilter = filter.copy(onlyWithSalary = input)

        saveFilterToSharedPreferences(updatedFilter)
    }

    override fun clearFilter() {
        saveFilterToSharedPreferences(FilterModel())
    }

    private fun doesFilterApplied(): Boolean {
        val filter = getFilterFromSharedPreferences()

        with(filter) {
            return !(countryName == null &&
                regionName == null &&
                industryName == null &&
                salary == null &&
                onlyWithSalary == null)
        }
    }

    private fun getFilterFromSharedPreferences(): FilterModel {
        val spFilter = sharedPreferences.getString(FILTER_OBJECT_KEY, "{}")
        return gson.fromJson(spFilter, FilterModel::class.java)
    }

    private fun saveFilterToSharedPreferences(filter: FilterModel) {
        sharedPreferences.edit().putString(FILTER_OBJECT_KEY, gson.toJson(filter)).apply()
    }
}
