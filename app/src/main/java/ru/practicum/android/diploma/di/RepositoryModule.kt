package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.converters.VacanciesConverter
import ru.practicum.android.diploma.data.search.SearchRepositoryImpl
import ru.practicum.android.diploma.domain.api.search.SearchRepository

val repositoryModule = module {

    factory { VacanciesConverter() }

    single<SearchRepository> {
        SearchRepositoryImpl(get(), get())
    }
}
