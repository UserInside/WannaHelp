package com.example.wannahelp.data.network

import com.example.wannahelp.domain.entities.CategoryItem
import com.example.wannahelp.domain.entities.FriendCardItem
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("categories")
    suspend fun getCategories(): List<CategoryItem>

    @POST("events")
    suspend fun getEvents(): List<NewsApiResponseItem>

    @GET("friends")
    suspend fun getFriends(): List<FriendCardItem>
}
