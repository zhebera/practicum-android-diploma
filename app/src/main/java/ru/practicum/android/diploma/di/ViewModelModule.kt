package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.ui.filter.workplace.countries.viewmodel.CountriesViewModel
import ru.practicum.android.diploma.ui.favourite.viewmodel.FavouriteViewModel
import ru.practicum.android.diploma.ui.details.viewmodel.VacancyDescriptionViewModel
import ru.practicum.android.diploma.ui.filter.viewmodel.FilterViewModel
import ru.practicum.android.diploma.ui.filter.workplace.regions.viewmodel.RegionsViewModel
import ru.practicum.android.diploma.ui.filter.industry.viewmodel.FilterIndustryViewModel
import ru.practicum.android.diploma.ui.search.viewmodel.SearchViewModel

val viewModelModule = module {

    viewModel { VacancyDescriptionViewModel(get(), get(), get()) }

    viewModel {
        SearchViewModel(get(), get())
    }

    viewModel {
        FavouriteViewModel(get())
    }

    viewModel {
        RegionsViewModel(get())
    }

    viewModel {
        CountriesViewModel(get())
    }

    viewModel {
        FilterIndustryViewModel(get())
    }

    viewModel {
        FilterViewModel(get())
    }
}
