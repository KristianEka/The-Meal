package com.ekachandra.themeal.di

import com.ekachandra.themeal.repository.MealRepository
import com.ekachandra.themeal.repository.MealRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single<MealRepository> {
        MealRepositoryImpl(
            get(),
            androidContext(),
        )
    }
}