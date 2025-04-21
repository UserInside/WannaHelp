package com.example.wannahelp.db.events

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventsEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    @ColumnInfo(name = "start_date")
    val startDate: Long,
    @ColumnInfo(name = "end_date")
    val endDate: Long,
    val description: String,
    val status: Int,
    val isRead: Boolean = false,
    val photos: List<String>,
    val category: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    val phone: String,
    val address: String,
    val organization: String,
)