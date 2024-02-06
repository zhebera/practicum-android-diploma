package ru.practicum.android.diploma.domain.impl.sharedpreferences

import ru.practicum.android.diploma.domain.api.sharedpreferences.SharedPreferencesInteractor
import ru.practicum.android.diploma.domain.api.sharedpreferences.SharedPreferencesRepository
import ru.practicum.android.diploma.domain.models.FilterModel

class SharedPreferencesInteractorImpl(
    private val repository: SharedPreferencesRepository
): SharedPreferencesInteractor {

    override fun getFilter(): FilterModel {
        return repository.getFilter()
    }

    override fun setCountry(name: String, id: String) {
        repository.setCountry(name, id)
    }

    override fun setRegion(name: String, id: String) {
        repository.setRegion(name, id)
    }

    override fun setIndustry(name: String, id: String) {
        repository.setIndustry(name, id)
    }

    override fun setSalary(input: String) {
        repository.setSalary(input)
    }

    override fun setSalaryCheckbox(input: Boolean) {
        repository.setSalaryCheckbox(input)
    }

    override fun doesFilterApplied(): Boolean {
        return repository.doesFilterApplied()
    }
}
