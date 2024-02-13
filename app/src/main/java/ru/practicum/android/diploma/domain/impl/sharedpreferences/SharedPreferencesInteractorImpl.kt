package ru.practicum.android.diploma.domain.impl.sharedpreferences

import ru.practicum.android.diploma.domain.api.sharedpreferences.SharedPreferencesInteractor
import ru.practicum.android.diploma.domain.api.sharedpreferences.SharedPreferencesRepository
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.FilterModel
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.Region

class SharedPreferencesInteractorImpl(
    private val repository: SharedPreferencesRepository
) : SharedPreferencesInteractor {

    override fun getFilter(): FilterModel? {
        return repository.getFilter()
    }

    override fun saveFilter(
        country: Country?,
        region: Region?,
        industry: Industry?,
        salary: String?,
        onlyWithSalary: Boolean?
    ) {
        repository.saveFilter(country, region, industry, salary, onlyWithSalary)
    }

    override fun clearFilter() {
        repository.clearFilter()
    }
}
