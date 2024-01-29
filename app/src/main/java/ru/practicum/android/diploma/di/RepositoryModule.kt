package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.converters.VacanciesConverter
import ru.practicum.android.diploma.data.converters.VacancyDbConverter
import ru.practicum.android.diploma.data.favourite.FavouriteRepositoryImpl
import ru.practicum.android.diploma.data.search.SearchRepositoryImpl
import ru.practicum.android.diploma.domain.api.favourite.FavouriteRepository
import ru.practicum.android.diploma.domain.api.search.SearchRepository

val repositoryModule = module {

    factory { VacanciesConverter() }

    factory{ VacancyDbConverter() }

    single<SearchRepository> {
        SearchRepositoryImpl(get(), get(), androidContext())
    }

    single<FavouriteRepository> {
        FavouriteRepositoryImpl(get(), get())
    }
}
