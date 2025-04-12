package com.example.wannahelp.network

import com.example.wannahelp.wannaHelpScreen.CategoryItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET("categories")
    suspend fun getCategories(): Map<String, CategoryItem>
}

@Serializable
data class CategoriesResponseModel(
//    @SerialName("categories")
    val categories: Map<String, CategoryItem>
){
    fun toList() = categories.values.toList()
}

object RetrofitClient {
    private const val BASE_URL = "http://46.17.104.59:3000/"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val okHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()



    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()


    val apiService: ApiService = retrofit.create(ApiService::class.java)
}

