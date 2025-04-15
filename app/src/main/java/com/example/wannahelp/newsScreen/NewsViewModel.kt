package com.example.wannahelp.newsScreen

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wannahelp.common.Category
import com.example.wannahelp.common.extentions.parseToList
import com.example.wannahelp.network.CategoryFilter
import com.example.wannahelp.network.RetrofitClient
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private var initialNewsList = MutableStateFlow<List<NewsItem>>(emptyList())

    private var sharedPref: SharedPreferences =
        application.applicationContext.getSharedPreferences(CHOSEN_CATEGORIES_KEY, MODE_PRIVATE)

    private val setOfReadNews = loadReadSetFromSharedPref()
    private val unreadMsgCount = sharedPref.getInt(UNREAD_MSG_COUNT, initialNewsList.value.size)
    val unreadMsgCountStateFlow = MutableStateFlow<Int>(unreadMsgCount)

    private var categoriesStateFlow =
        MutableStateFlow<Set<String>>(
            sharedPref.getStringSet(CHOSEN_CATEGORIES_KEY, null) ?: Category.entries.map { it.name }
                .toSet(),
        )

    val screenStateFlow = MutableStateFlow<NewsState>(NewsState.Progress)
    val listToShowStateFlow = initialNewsList

    init {
        viewModelScope.launch {
            initialNewsList = loadInitialNewsList()
            updateListToShow()
        }
    }

    private suspend fun loadInitialNewsList(): MutableStateFlow<List<NewsItem>> {
        screenStateFlow.value = NewsState.Progress
        var resultList = MutableStateFlow<List<NewsItem>>(emptyList())

        val exHandler = CoroutineExceptionHandler { _, _ ->
            Log.i("DEMO", "try news from file") // для демонстрации
            resultList = MutableStateFlow<List<NewsItem>>(
                Json.parseToList<NewsItem>(
                    getApplication<Application>().applicationContext,
                    NEWS_FILE_NAME,
                )
            )
            Log.i("DEMO", "news loaded from file") // для демонстрации
        }

        resultList = viewModelScope.async(exHandler) {
            Log.i("DEMO", "try news from api") // для демонстрации
            val response = RetrofitClient.apiService.getEvents()
            val listOfNewsItem = mutableListOf<NewsItem>()
            response.forEach {
                listOfNewsItem.add(NewsApiResponseItem.mapResponseItemToNewsItem(it))
            }
            MutableStateFlow<List<NewsItem>>(listOfNewsItem) // todo refactoring
        }.await().also {
            Log.i("DEMO", "news loaded from api") // для демонстрации
        }
        return resultList.also {
            Log.i("DEMO", "resultListFromApi ${it.value}") // для демонстрации
            screenStateFlow.value = NewsState.Done()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun updateListToShow() {
        updateCategories()
        withContext(Dispatchers.IO) {
            categoriesStateFlow.flatMapLatest { categories ->
                flow {
                    val result =
                        initialNewsList.map { newsList ->
                            newsList.filter { newsItem ->
                                categories.contains(newsItem.category.toString())
                            }.apply {
                                countUnreadMsg(this)
                            }
                        }
                    emit(result)
                }
            }.collect { result ->
                result.collect { listToShowStateFlow.value = it }
            }
        }
    }

    private fun updateCategories() {
        categoriesStateFlow.value =
            sharedPref.getStringSet(CHOSEN_CATEGORIES_KEY, null) ?: Category.entries.map { it.name }
                .toSet()
    }

    private fun countUnreadMsg(list: List<NewsItem>) {
        var unreadCount = list.size
        list.forEach { newsItem ->
            if (setOfReadNews.contains(newsItem)) unreadCount--
            unreadMsgCountStateFlow.value = unreadCount
        }
        sharedPref.edit { putInt(UNREAD_MSG_COUNT, unreadCount) }
    }

    fun addReadItemToSet(newsItem: NewsItem) {
        setOfReadNews.add(newsItem)
        saveReadMsgSetToSharedPref()
        viewModelScope.launch { updateListToShow() }
    }

    private fun saveReadMsgSetToSharedPref() {
        val serializedSetToSave = mutableSetOf<String>()
        setOfReadNews.forEach {
            serializedSetToSave.add(Json.encodeToString(it))
        }
        sharedPref.edit { putStringSet(READ_NEWS_KEY, serializedSetToSave) }
    }

    private fun loadReadSetFromSharedPref(): MutableSet<NewsItem> {
        val setToDeserialize = sharedPref.getStringSet(READ_NEWS_KEY, null)
        val result = mutableSetOf<NewsItem>()
        setToDeserialize?.forEach {
            result.add(Json.decodeFromString(it))
        }
        return result
    }

    companion object {
        const val CHOSEN_CATEGORIES_KEY = "chosenCategories"
        private const val READ_NEWS_KEY = "readNews"
        private const val UNREAD_MSG_COUNT = "unreadMsgCount"
        private const val NEWS_FILE_NAME = "news.json"
    }
}

sealed class NewsState {
    object Progress : NewsState()
    class Done() : NewsState()
}
