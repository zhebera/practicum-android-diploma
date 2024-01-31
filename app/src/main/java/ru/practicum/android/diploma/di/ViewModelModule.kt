package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.ui.search.viewmodel.VacancyDescriptionViewModel
import ru.practicum.android.diploma.ui.search.viewmodel.SearchViewModel

val viewModelModule = module {

    viewModel { parameters -> VacancyDescriptionViewModel(get(), get(), vacancyId = parameters.get()) }

    viewModel { SearchViewModel(get()) }
}
