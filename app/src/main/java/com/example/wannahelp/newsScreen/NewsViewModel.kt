package com.example.wannahelp.newsScreen

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wannahelp.R
import com.example.wannahelp.common.Category
import com.example.wannahelp.common.extentions.parseToList
import com.example.wannahelp.newsScreen.newsFilterScreen.FilterCategoryCard
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private val initialNewsList = getNewsList()
    private var sharedPref: SharedPreferences =
        application.applicationContext.getSharedPreferences(CHOSEN_CATEGORIES_KEY, MODE_PRIVATE)

    private val setOfReadNews = loadReadSetFromSharedPref()
    private val unreadMsgCount = sharedPref.getInt(UNREAD_MSG_COUNT, initialNewsList.size)
    val unreadMsgCountStateFlow = MutableStateFlow<Int>(unreadMsgCount)

    private var setOfChosenCategories =
        sharedPref.getStringSet(CHOSEN_CATEGORIES_KEY, null) ?: Category.entries.map { it.name }
            .toSet()

    private var categoriesStateFlow = MutableStateFlow<Set<String>>(setOfChosenCategories)

    val screenStateFlow = MutableStateFlow<NewsState>(NewsState.Done())
    val listToShowStateFlow = MutableStateFlow<List<NewsItem>>(emptyList())

    init {
        viewModelScope.launch { updateListToShow() }
    }

    private suspend fun loadNewsWithProgress(): MutableStateFlow<List<NewsItem>> {
        for (i in 1..100 step 34) {
            delay(100)
            screenStateFlow.value = NewsState.Progress(i)
        }
        return MutableStateFlow<List<NewsItem>>(initialNewsList).also {
            screenStateFlow.value = NewsState.Done()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun updateListToShow() {
        withContext(Dispatchers.IO) {
            categoriesStateFlow.flatMapLatest { categories ->
                flow {
                    val result =
                        loadNewsWithProgress().map { newsList ->
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

    private fun countUnreadMsg(list: List<NewsItem>) {
        var unreadCount = list.size
        list.forEach { newsItem ->
            if (setOfReadNews.contains(newsItem)) unreadCount--
            unreadMsgCountStateFlow.value = unreadCount
        }
        sharedPref.edit { putInt(UNREAD_MSG_COUNT, unreadCount) }
    }

    @SuppressLint("CheckResult")
    private fun getNewsList(): List<NewsItem> {
        return Json.parseToList<NewsItem>(
            getApplication<Application>().applicationContext,
            NEWS_FILE_NAME,
        )
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

    val listOfCategoryFiltersToShow =
        getInitialFilterList(application).map { list ->
            list.map { item ->
                if (setOfChosenCategories.contains(item.category.toString())) {
                    item.copy(isChecked = true)
                } else {
                    item.copy(isChecked = false)
                }
            }
        }

    private val tmpSetOfFilteredCategoriesToSave = setOfChosenCategories.toMutableSet()

    fun addNewsItemToFilter(category: String) = tmpSetOfFilteredCategoriesToSave.add(category)

    fun removeNewsItemFromFilter(category: String) = tmpSetOfFilteredCategoriesToSave.remove(category)

    fun saveChosenCategories() {
        viewModelScope.launch { updateListToShow() }
        setOfChosenCategories = tmpSetOfFilteredCategoriesToSave.toSet()
        sharedPref.edit().apply {
            putStringSet(CHOSEN_CATEGORIES_KEY, tmpSetOfFilteredCategoriesToSave)
            apply()
        }
        categoriesStateFlow.value = tmpSetOfFilteredCategoriesToSave.toSet()
    }

    private fun getInitialFilterList(application: Application) =
        Observable.just<List<FilterCategoryCard>>(
            mutableListOf(
                FilterCategoryCard(
                    application.resources.getString(R.string.tv_cat_kids),
                    Category.KIDS,
                ),
                FilterCategoryCard(
                    application.resources.getString(R.string.tv_cat_adults),
                    Category.ADULTS,
                ),
                FilterCategoryCard(
                    application.resources.getString(R.string.tv_cat_events),
                    Category.EVENTS,
                ),
                FilterCategoryCard(
                    application.resources.getString(R.string.tv_cat_aged),
                    Category.AGED,
                ),
                FilterCategoryCard(
                    application.resources.getString(R.string.tv_cat_animals),
                    Category.ANIMALS,
                ),
            ),
        )

    companion object {
        private const val CHOSEN_CATEGORIES_KEY = "chosenCategories"
        private const val READ_NEWS_KEY = "readNews"
        private const val UNREAD_MSG_COUNT = "unreadMsgCount"
        private const val NEWS_FILE_NAME = "news.json"
        const val NEWS_FILE_NAME_KEY = "news"
    }
}

sealed class NewsState {
    class Progress(val progress: Int) : NewsState()

    class Done() : NewsState()
}
