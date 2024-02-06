package ru.practicum.android.diploma.data.sharedpreferences

import android.content.Context
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.api.sharedpreferences.SharedPreferencesRepository
import ru.practicum.android.diploma.domain.models.FilterModel
import ru.practicum.android.diploma.util.CAREER_KEY_APP_PREFERENCES
import ru.practicum.android.diploma.util.FILTER_OBJECT_KEY


class SharedPreferencesRepositoryImpl(
    context: Context
): SharedPreferencesRepository {
    private val androidContext = context
    private var gson = Gson()

    private val sharedPreferences = androidContext.getSharedPreferences(CAREER_KEY_APP_PREFERENCES, Context.MODE_PRIVATE)

    override fun getFilter(): FilterModel {
        val json = sharedPreferences.getString(FILTER_OBJECT_KEY, "")
        return gson.fromJson(json, FilterModel::class.java)
    }

    override fun setCountry(name: String, id: String) {
        val spFilter = sharedPreferences.getString(FILTER_OBJECT_KEY, "")
        val filter = gson.fromJson(spFilter, FilterModel::class.java)
        val updatedFilter = filter.copy(countryName = name, countryId = id)

        sharedPreferences.edit().putString(FILTER_OBJECT_KEY, gson.toJson(updatedFilter)).apply()
    }

    override fun setRegion(name: String, id: String) {
        val spFilter = sharedPreferences.getString(FILTER_OBJECT_KEY, "")
        val filter = gson.fromJson(spFilter, FilterModel::class.java)
        val updatedFilter = filter.copy(regionName = name, regionId = id)

        sharedPreferences.edit().putString(FILTER_OBJECT_KEY, gson.toJson(updatedFilter)).apply()
    }

    override fun setIndustry(name: String, id: String) {
        val spFilter = sharedPreferences.getString(FILTER_OBJECT_KEY, "")
        val filter = gson.fromJson(spFilter, FilterModel::class.java)
        val updatedFilter = filter.copy(industryName = name, industryId = id)

        sharedPreferences.edit().putString(FILTER_OBJECT_KEY, gson.toJson(updatedFilter)).apply()
    }

    override fun setSalary(input: String) {
        val spFilter = sharedPreferences.getString(FILTER_OBJECT_KEY, "")
        val filter = gson.fromJson(spFilter, FilterModel::class.java)
        val updatedFilter = filter.copy(salary = input)

        sharedPreferences.edit().putString(FILTER_OBJECT_KEY, gson.toJson(updatedFilter)).apply()
    }

    override fun setSalaryCheckbox(input: Boolean) {
        val spFilter = sharedPreferences.getString(FILTER_OBJECT_KEY, "")
        val filter = gson.fromJson(spFilter, FilterModel::class.java)
        val updatedFilter = filter.copy(onlyWithSalary = input)

        sharedPreferences.edit().putString(FILTER_OBJECT_KEY, gson.toJson(updatedFilter)).apply()
    }

    override fun doesFilterApplied(): Boolean {
        val spFilter = sharedPreferences.getString(FILTER_OBJECT_KEY, "")
        val filter = gson.fromJson(spFilter, FilterModel::class.java)

        with(filter) {
            return !(countryName == null &&
                regionName == null &&
                industryName == null &&
                salary == null &&
                onlyWithSalary == null)
        }

    }
}
