package com.example.wannahelp.newsScreen

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wannahelp.common.Category
import com.example.wannahelp.db.mapEventDbEntityToNewsItem
import com.example.wannahelp.newsScreen.newsFilterScreen.DataStoreManager
import com.example.wannahelp.wannaHelpScreen.MainApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    val dsm = DataStoreManager(application)

    val screenStateFlow = MutableStateFlow<NewsState>(NewsState.Progress)

    val listToShowStateFlow: MutableStateFlow<List<NewsItem>> =
        MutableStateFlow<List<NewsItem>>(emptyList())

    val unreadMsgCountStateFlow =
        MutableStateFlow<Int>(listToShowStateFlow.value.count { it.isRead == false })

    val setOfChosenCategories = dsm.getSelectedCategoriesSet().stateIn(
        viewModelScope, SharingStarted.Eagerly, null
    )


    init {
        Log.i("DSTORE", "--- onInit NewsScreen ${setOfChosenCategories.value}")
        viewModelScope.launch {
            loadNewsFromDB()
        }
    }

    suspend fun loadNewsFromDB(){
        screenStateFlow.value = NewsState.Progress
//        val catList = readCategoriesFromDataStore()
        val catList = emptyList<String>() //todo
        val events = MainApp.database.getEventsDao()
                .getEvents(catList)
                .map { mapEventDbEntityToNewsItem(it) }
        listToShowStateFlow.value = events
        unreadMsgCountStateFlow.value = events.count { !it.isRead }
        screenStateFlow.value = NewsState.Done
    }

//    private suspend fun readCategoriesFromDataStore(): List<String> {
//        val prefKey = stringSetPreferencesKey(CHOSEN_CATEGORIES)
//        val preferences = categoriesDataStore.data.firstOrNull()
//        val result = preferences?.get(prefKey)?.toList() ?: getInitialList()
//        Log.e("DSTORE", "result $result")
//        return result
//    }

    fun markNewsItemAsRead(newsItem: NewsItem) {
        viewModelScope.launch {
            MainApp.database.getEventsDao().markEventAsRead(newsItem.id)
        }
    }

    private fun getInitialList() =
        listOf(
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
