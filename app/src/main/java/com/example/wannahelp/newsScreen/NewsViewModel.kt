package com.example.wannahelp.newsScreen

import android.annotation.SuppressLint
import android.app.Application
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wannahelp.common.Category
import com.example.wannahelp.common.datastore
import com.example.wannahelp.db.mapEventDbEntityToNewsItem
import com.example.wannahelp.wannaHelpScreen.MainApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    val ctx = application.applicationContext

    private val initialListToShow = getFullCatList()

    val screenStateFlow = MutableStateFlow<NewsState>(NewsState.Progress)

    val listToShowStateFlow: MutableStateFlow<List<NewsItem>> =
        MutableStateFlow<List<NewsItem>>(emptyList())

    val unreadMsgCountStateFlow =
        MutableStateFlow<Int>(listToShowStateFlow.value.count { it.isRead == false })

    init {
        viewModelScope.launch {
            loadNewsFromDB()
        }
    }

    suspend fun loadNewsFromDB() {
        val setOfChosenCategories = getChosenCategoriesFromDS().map { it.lowercase() }.toSet()
        screenStateFlow.value = NewsState.Progress

        val eventsList =
            MainApp.database.getEventsDao()
                .getEvents(setOfChosenCategories)
                .map { mapEventDbEntityToNewsItem(it) }

        listToShowStateFlow.value = eventsList
        unreadMsgCountStateFlow.value = eventsList.count { !it.isRead }

        screenStateFlow.value = NewsState.Done
    }

    private suspend fun getChosenCategoriesFromDS(): Set<String> {
        return ctx.datastore.data.map { preference ->
            preference[stringSetPreferencesKey(CHOSEN_CATEGORIES)] ?: initialListToShow
        }.first()
    }

    fun markNewsItemAsRead(newsItem: NewsItem) {
        viewModelScope.launch {
            MainApp.database.getEventsDao().markEventAsRead(newsItem.id)
        }
    }

    private fun getFullCatList() =
        setOf(
            Category.KIDS.name,
            Category.ADULTS.name,
            Category.EVENTS.name,
            Category.AGED.name,
            Category.ANIMALS.name,
        )

    companion object {
        const val CHOSEN_CATEGORIES = "chosenCategories"
    }
}

sealed class NewsState {
    object Progress : NewsState()

    object Done : NewsState()
}
