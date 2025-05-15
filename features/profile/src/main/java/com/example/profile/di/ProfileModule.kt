package com.example.profile.di

import com.example.data.network.ApiService
import com.example.profile.ProfileViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProfileModule {

    @Singleton
    @Provides
    fun provideProfileViewModelFactory(apiService: ApiService): ProfileViewModelFactory {
        return ProfileViewModelFactory(apiService)
    }


}