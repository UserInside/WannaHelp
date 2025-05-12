package com.example.wannahelp.di

import android.content.Context
import com.example.data.network.ApiService
import com.example.wannahelp.presentation.profileScreen.ProfileViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideJson() =
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

    @Provides
    fun provideProfileViewModelFactory(apiService: ApiService): ProfileViewModelFactory {
        return ProfileViewModelFactory(apiService)
    }
}