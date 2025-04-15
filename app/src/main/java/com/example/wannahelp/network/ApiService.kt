package com.example.wannahelp.network

import com.example.wannahelp.newsScreen.NewsApiResponseItem
import com.example.wannahelp.newsScreen.NewsItem
import com.example.wannahelp.wannaHelpScreen.CategoryItem
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface ApiService {
    @GET("categories")
    suspend fun getCategories(): Map<String, CategoryItem>

    @GET("events")
    suspend fun getEvents(): List<NewsApiResponseItem>

//    @POST("events/{filter}")
//    suspend fun getEventsById(id: String?): Map<String, List<NewsItem>>
}

data class CategoryFilter(val categoryName: String)

object RetrofitClient {
    private const val BASE_URL = "http://46.17.104.59:3000/"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val okHttpClient =
        OkHttpClient.Builder()
            .callTimeout(3, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(okHttpClient)
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}

