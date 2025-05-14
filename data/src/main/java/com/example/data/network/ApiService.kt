package com.example.data.network

import com.example.domain.entities.CategoryItem
import com.example.domain.entities.FriendCardDomainModel
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("categories")
    suspend fun getCategories(): List<CategoryItem>  // todo заменить на дата

    @POST("events")
    suspend fun getEvents(): List<NewsApiResponseItem> // todo заменить на дата

    @GET("friends")
    suspend fun getFriends(): List<FriendCardDomainModel>  // todo заменить на дата
}
