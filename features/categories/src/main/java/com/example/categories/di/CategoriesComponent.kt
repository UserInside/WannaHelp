package com.example.categories.di

import com.example.data.di.DataModule
import com.example.domain.di.DomainModule
import com.example.categories.CategoriesViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, DomainModule::class])
interface CategoriesComponent {
    fun inject(vm: CategoriesViewModel)
}