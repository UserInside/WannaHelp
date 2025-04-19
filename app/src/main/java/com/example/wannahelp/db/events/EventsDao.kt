package com.example.wannahelp.db.events

import android.provider.CalendarContract
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
interface EventsDao {

    @Insert(onConflict = REPLACE)
    fun  addEvent(event: EventsEntity)

    @Query("SELECT * FROM events")
    fun getEvents() {
//todo add filter
    }
}