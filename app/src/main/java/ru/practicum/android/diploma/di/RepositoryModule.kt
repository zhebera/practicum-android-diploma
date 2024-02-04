package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.converters.VacanciesConverter
import ru.practicum.android.diploma.data.converters.VacanciesDescriptionConverter
import ru.practicum.android.diploma.data.converters.VacancyDbConverter
import ru.practicum.android.diploma.data.favourite.FavouriteRepositoryImpl
import ru.practicum.android.diploma.data.search.SearchRepositoryImpl
import ru.practicum.android.diploma.data.details.DetailsRepositoryImpl
import ru.practicum.android.diploma.data.industries.IndustriesRepositoryImpl
import ru.practicum.android.diploma.domain.api.favourite.FavouriteRepository
import ru.practicum.android.diploma.domain.api.search.SearchRepository
import ru.practicum.android.diploma.domain.api.details.DetailsRepository
import ru.practicum.android.diploma.domain.api.industries.IndustriesRepository

val repositoryModule = module {

    factory { VacanciesConverter() }

    factory { VacanciesDescriptionConverter() }

    factory { VacancyDbConverter() }

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

    single<FavouriteRepository> {
        FavouriteRepositoryImpl(get(), get())
    }

    single<IndustriesRepository> {
        IndustriesRepositoryImpl()
    }
}
