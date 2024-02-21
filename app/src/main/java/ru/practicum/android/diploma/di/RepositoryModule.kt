package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.converters.CountriesConverter
import ru.practicum.android.diploma.data.converters.IndustriesConverter
import ru.practicum.android.diploma.data.converters.RegionsConverter
import ru.practicum.android.diploma.data.converters.VacanciesConverter
import ru.practicum.android.diploma.data.converters.VacanciesDescriptionConverter
import ru.practicum.android.diploma.data.converters.db.VacancyDbConverter
import ru.practicum.android.diploma.data.impl.countries.CountriesRepositoryImpl
import ru.practicum.android.diploma.data.impl.details.DetailsRepositoryImpl
import ru.practicum.android.diploma.data.impl.favourite.FavouriteRepositoryImpl
import ru.practicum.android.diploma.data.impl.industries.IndustriesRepositoryImpl
import ru.practicum.android.diploma.data.impl.search.SearchRepositoryImpl
import ru.practicum.android.diploma.data.impl.regions.RegionsRepositoryImpl
import ru.practicum.android.diploma.data.sharedpreferences.SharedPreferencesRepositoryImpl
import ru.practicum.android.diploma.domain.api.countries.CountriesRepository
import ru.practicum.android.diploma.domain.api.details.DetailsRepository
import ru.practicum.android.diploma.domain.api.favourite.FavouriteRepository
import ru.practicum.android.diploma.domain.api.industries.IndustriesRepository
import ru.practicum.android.diploma.domain.api.search.SearchRepository
import ru.practicum.android.diploma.domain.api.regions.RegionsRepository
import ru.practicum.android.diploma.domain.api.sharedpreferences.SharedPreferencesRepository

val repositoryModule = module {

    factory { VacanciesConverter() }

    factory { VacanciesDescriptionConverter() }

    factory { VacancyDbConverter() }

    factory { IndustriesConverter }

    factory { CountriesConverter() }

    factory { RegionsConverter() }

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
        FavouriteRepositoryImpl(get(), get(), androidContext())
    }

    single<RegionsRepository> {
        RegionsRepositoryImpl(get(), get(), androidContext())
    }

    single<IndustriesRepository> {
        IndustriesRepositoryImpl(get(), get(), androidContext())
    }

    single<CountriesRepository> {
        CountriesRepositoryImpl(get(), get(), androidContext())
    }

    single<SharedPreferencesRepository> {
        SharedPreferencesRepositoryImpl(androidContext())
    }
}
