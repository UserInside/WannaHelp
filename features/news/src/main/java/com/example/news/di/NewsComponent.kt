package com.example.news.di

import com.example.data.di.DataModule
import com.example.domain.di.DomainModule
import com.example.news.NewsViewModel
import dagger.Component

@Component(modules = [DomainModule::class, DataModule::class])
interface NewsComponent {
    fun inject(vm: NewsViewModel)
}