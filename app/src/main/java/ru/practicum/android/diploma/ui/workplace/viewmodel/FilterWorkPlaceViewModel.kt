package ru.practicum.android.diploma.ui.workplace.viewmodel

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.api.sharedpreferences.SharedPreferencesInteractor
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Region

class FilterWorkPlaceViewModel(private val interactor: SharedPreferencesInteractor): ViewModel() {

    fun saveWorkPlace(region: Region?, country: Country?) {
        region?.let {
            interactor.setRegion(it.name!!, it.id!!)
        }

        country?.let {
            interactor.setCountry(it.name, it.id)
        }
    }

}
