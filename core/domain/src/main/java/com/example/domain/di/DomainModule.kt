package com.example.domain.di

import com.example.domain.interactors.CategoriesInteractor
import com.example.domain.repository.CategoriesRepository
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideCategoryInteractor(repository: CategoriesRepository): CategoriesInteractor =
        CategoriesInteractor(repository)
}