package com.example.wannahelp.network

import com.example.wannahelp.MainApp
import com.example.wannahelp.newsScreen.NewsApiResponseItem
import com.example.wannahelp.profileScreen.FriendCard
import com.example.wannahelp.wannaHelpScreen.CategoryItem
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface ApiService {
    @GET("categories")
    suspend fun getCategories(): List<CategoryItem>

    @POST("events")
    suspend fun getEvents(): List<NewsApiResponseItem>

    @GET("friends")
    suspend fun getFriends(): List<FriendCard>
}

class RetrofitClient(retrofit: Retrofit) {
//    @Inject lateinit var retrofit: Retrofit
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
