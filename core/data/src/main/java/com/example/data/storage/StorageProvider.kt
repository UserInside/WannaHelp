package com.example.data.storage

import android.content.Context
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.common.utils.extensions.datastore
import kotlinx.coroutines.flow.Flow

interface StorageProvider {
    val data: Flow<Preferences>
    suspend fun updateData(transform: (MutablePreferences) -> Unit)
}

class DatastoreStorageProvider (private val context: Context) : StorageProvider {

    private val datastore = context.datastore
    override val data = datastore.data

    override suspend fun updateData(transform: (MutablePreferences) -> Unit) {
        datastore.edit(transform)
    }
}