package com.example.data.network

import com.example.data.db.categories.CategoriesEntity
import com.example.data.db.events.EventsEntity
import com.example.domain.entities.FriendCardDomainModel
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("categories")
    suspend fun getCategories(): List<CategoriesEntity>

    @POST("events")
    suspend fun getEvents(): List<EventsEntity>

    @GET("friends")
    suspend fun getFriends(): List<FriendCardDomainModel>
}
