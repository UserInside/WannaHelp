package com.example.wannahelp.db.events

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
interface EventsDao {
    @Insert(onConflict = REPLACE)
    suspend fun addEvent(event: EventsEntity)

    @Query("SELECT * FROM events WHERE category IN (:categories)")
    suspend fun getEvents(categories: Set<String>): List<EventsEntity>

    @Query("UPDATE events SET isRead = true WHERE id = :id")
    suspend fun markEventAsRead(id: Int)
}
