package ru.practicum.android.diploma.data.sharedpreferences

import android.content.Context
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.api.sharedpreferences.SharedPreferencesRepository
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.FilterModel
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.util.CAREER_KEY_APP_PREFERENCES
import ru.practicum.android.diploma.util.FILTER_OBJECT_KEY

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

    override fun saveFilter(
        country: Country?,
        region: Region?,
        industry: Industry?,
        salary: String?,
        onlyWithSalary: Boolean?
    ) {
        val filter = FilterModel(
            countryName = country?.name,
            countryId = country?.id,
            regionName = region?.name,
            regionId = region?.id,
            industryName = industry?.name,
            industryId = industry?.id,
            salary = salary,
            onlyWithSalary = onlyWithSalary
        )

        saveFilterToSharedPreferences(filter)
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
