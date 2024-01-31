package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.converters.VacanciesConverter
import ru.practicum.android.diploma.data.converters.VacanciesDescriptionConverter
import ru.practicum.android.diploma.data.search.SearchRepositoryImpl
import ru.practicum.android.diploma.data.details.DetailsRepositoryImpl
import ru.practicum.android.diploma.domain.api.search.SearchRepository
import ru.practicum.android.diploma.domain.api.details.DetailsRepository

val repositoryModule = module {

    factory { VacanciesConverter() }

    factory { VacanciesDescriptionConverter() }

    single<SearchRepository> {
        SearchRepositoryImpl(
            networkClient = get(),
            vacanciesConverter = get(),
            vacanciesDescriptionConverter = get(),
            context = androidContext()
        )
    }

    single<DetailsRepository> {
        DetailsRepositoryImpl(androidContext())
    }
}
