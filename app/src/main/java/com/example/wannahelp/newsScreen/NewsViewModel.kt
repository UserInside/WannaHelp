package com.example.wannahelp.newsScreen

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wannahelp.common.Category
import com.example.wannahelp.common.extentions.parseToList
import com.example.wannahelp.db.events.EventsDao
import com.example.wannahelp.db.mapEventDbEntityToNewsItem
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class NewsViewModelFactory(
    private val application: Application,
    private val dao: EventsDao,
) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(
            application, dao
        ) as T
    }
}

class NewsViewModel(application: Application, private val dao: EventsDao) :
    AndroidViewModel(application) {

    private var sharedPref: SharedPreferences =
        application.applicationContext.getSharedPreferences(CHOSEN_CATEGORIES_KEY, MODE_PRIVATE)

    private var categoriesStateFlow = MutableStateFlow<Set<String>>(
        sharedPref.getStringSet(CHOSEN_CATEGORIES_KEY, null) ?: Category.entries.map { it.name }
            .toSet(),
    )

    val screenStateFlow = MutableStateFlow<NewsState>(NewsState.Progress)

    val listToShowStateFlow = MutableStateFlow<List<NewsItem>>(emptyList())
    val unreadMsgCountStateFlow = MutableStateFlow<Int>(0)
//        listToShowStateFlow.value.count { it.isRead == false })


    fun loadNewsFromDb() {
        screenStateFlow.value = NewsState.Progress
        val exHandler = CoroutineExceptionHandler { _, _ ->
            Log.i("DEMO", "try news from file") // для демонстрации
            listToShowStateFlow.value = Json.parseToList<NewsItem>(
                getApplication<Application>().applicationContext,
                NEWS_FILE_NAME,
            )
            Log.i("DEMO", "news loaded from file") // для демонстрации
        }

        viewModelScope.launch(Dispatchers.IO + exHandler) {
            Log.i("DEMO", "try news from db") // для демонстрации
            val requestCategoriesList = categoriesStateFlow.value.map {
                it.lowercase()
            }.toList()
            listToShowStateFlow.value =
                dao.getEvents(requestCategoriesList).map { mapEventDbEntityToNewsItem(it) }
            Log.i("DEMO", "news loaded from db") // для демонстрации
            unreadMsgCountStateFlow.value = listToShowStateFlow.value.count{
                it.isRead == false
            }
            screenStateFlow.value = NewsState.Done()
        }
    }

//    @OptIn(ExperimentalCoroutinesApi::class)
//    suspend fun updateListToShow() {
//        updateChosenCategories()
//        withContext(Dispatchers.IO) {
//            Log.i("NVM","categoriesStateFlow.value ${categoriesStateFlow.value}") // для демонстрации
//            val catRequest = categoriesStateFlow.value.map {
//                it.lowercase()
//            }.toList()
//            Log.i("NVM", "catReguest $catRequest") // для демонстрации
//
//            val newsFromDb = dao.getEvents(listOf("aged"))
//            Log.i("NVM", "categoriesFromDb $newsFromDb") // для демонстрации
//
//            listToShowStateFlow.value = newsFromDb.value.map { mapEventDbEntityToNewsItem(it) }.also {
//                Log.i("NVM", "newsItemList $it") // для демонстрации
//            }
//
////            flow {
////                emit(newsItemList)
////            }.collect { list ->
//////                countUnreadMsg(list)
////                unreadMsgCountStateFlow.value = list.count { it.isRead }
////                listToShowStateFlow.value = list
////            }
//        }
//    }

    private fun updateChosenCategories() {
        Log.i("DEMO", "fun updateChosenCategories") // для демонстрации
        categoriesStateFlow.value =
            sharedPref.getStringSet(CHOSEN_CATEGORIES_KEY, null) ?: Category.entries.map { it.name }
                .toSet()
    }

    fun markNewsItemAsRead(newsItem: NewsItem) {
        viewModelScope.launch {
            dao.markEventAsRead(newsItem.id)
        }
    }

    companion object {
        const val CHOSEN_CATEGORIES_KEY = "chosenCategories"
        private const val NEWS_FILE_NAME = "news.json"
    }
}

sealed class NewsState {
    object Progress : NewsState()
    class Done() : NewsState()
}
