package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.converters.VacanciesConverter
import ru.practicum.android.diploma.data.search.SearchRepositoryImpl
import ru.practicum.android.diploma.domain.api.SearchRepository

val repositoryModule = module {

    factory { VacanciesConverter() }

    single<SearchRepository> {
        SearchRepositoryImpl(get(), get(), androidContext())
    }
}
