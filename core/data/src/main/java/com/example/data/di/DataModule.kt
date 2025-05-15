package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.BuildConfig
import com.example.data.CategoriesRepositoryImpl
import com.example.data.NewsRepositoryImpl
import com.example.data.db.AppDatabase
import com.example.data.db.categories.CategoriesDao
import com.example.data.db.events.EventsDao
import com.example.data.network.ApiService
import com.example.domain.interactors.CategoriesInteractor
import com.example.domain.interactors.NewsInteractor
import com.example.domain.repository.CategoriesRepository
import com.example.domain.repository.NewsRepository
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
class DataModule(val appContext: Context) {

    @Provides
    fun provideContext() = appContext

    @Provides
    @Singleton
    fun provideOkHttp() =
        OkHttpClient.Builder()
            .callTimeout(3, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    },
            ).build()

    @Provides
    @Singleton
    fun provideJson() =
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

    @Provides
    @Singleton
    fun provideApiService(
        okHttpClient: OkHttpClient,
        json: Json,
    ): ApiService {
        val baseUrl = BuildConfig.BASE_URL

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(appContext: Context): AppDatabase =
        Room.databaseBuilder(
            context = appContext,
            klass = AppDatabase::class.java,
            name = "wanna_help_database",
        ).build()

    @Provides
    fun provideEventsDao(db: AppDatabase): EventsDao = db.getEventsDao()

    @Provides
    fun provideCategoriesDao(db: AppDatabase): CategoriesDao = db.getCategoriesDao()

    @Provides
    fun provideCategoriesRepository(
        context: Context,
        categoriesDao: CategoriesDao
    ): CategoriesRepository = CategoriesRepositoryImpl(context, categoriesDao)

//    @Provides
//    fun provideCategoriesInteractor(categoriesRepository: CategoriesRepository): CategoriesInteractor =
//        CategoriesInteractor(categoriesRepository)

    @Provides
    fun provideNewsRepository(
        db: AppDatabase
    ): NewsRepository = NewsRepositoryImpl(db)

    @Provides
    fun provideNewsInteractor(newsRepository: NewsRepository): NewsInteractor =
        NewsInteractor(newsRepository)
}