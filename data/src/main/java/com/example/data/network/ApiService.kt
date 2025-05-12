package com.example.data.network

import com.example.domain.entities.CategoryItem
import com.example.domain.entities.FriendCardItem
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("categories")
    suspend fun getCategories(): List<CategoryItem>

    @POST("events")
    suspend fun getEvents(): List<NewsApiResponseItem> // todo заменить на дата

    @GET("friends")
    suspend fun getFriends(): List<FriendCardItem>
}
