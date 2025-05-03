package com.example.wannahelp.di

import android.content.Context
import com.example.wannahelp.data.network.ApiService
import com.example.wannahelp.presentation.profileScreen.ProfileViewModel
import com.example.wannahelp.presentation.profileScreen.ProfileViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule(val appContext: Context) {

    @Provides
    fun provideContext() = appContext

    @Provides
    @Singleton
    fun provideOkHttp() = OkHttpClient.Builder()
        .callTimeout(3, TimeUnit.SECONDS)
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                },
        ).build()

    @Provides
    @Singleton
    fun provideJson() =  Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient, json: Json): ApiService {
        val BASE_URL = "http://46.17.104.59:3000/"

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    fun provideProfileViewModelFactory(apiService: ApiService): ProfileViewModelFactory {
        return ProfileViewModelFactory(apiService)

    }
}

