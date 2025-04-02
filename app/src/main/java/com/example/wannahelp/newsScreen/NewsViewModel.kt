package com.example.wannahelp.newsScreen

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import com.example.wannahelp.R
import com.example.wannahelp.common.Category
import com.example.wannahelp.common.extentions.parseToList
import com.example.wannahelp.newsScreen.newsFilterScreen.FilterCategoryCard
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.serialization.json.Json

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private val initialNewsList = getNewsList()

    private var sharedPref: SharedPreferences =
        application.applicationContext.getSharedPreferences(CHOSEN_CATEGORIES_KEY, MODE_PRIVATE)

    private val screenStateSubject = BehaviorSubject.createDefault<NewsState>(NewsState.Done())
    val screenStateObservable: Observable<NewsState> = screenStateSubject

    private val setOfReadNews = loadReadSetFromSharedPref()
    val unreadMsgCount = sharedPref.getInt(UNREAD_MSG_COUNT, initialNewsList.size)

    private val unreadCountSubject: BehaviorSubject<Int> =
        BehaviorSubject.createDefault(unreadMsgCount)
    val unreadCountObs: Observable<Int> = unreadCountSubject

    private var setOfChosenCategories =
        sharedPref.getStringSet(CHOSEN_CATEGORIES_KEY, null) ?: Category.entries.map { it.name }
            .toSet()

    private var categoriesSubject = BehaviorSubject.createDefault(setOfChosenCategories)

    val listToShow: Observable<List<NewsItem>> =
        categoriesSubject
            .switchMap { categories ->
                loadNewsWithProgress()
                    .map { newsList ->
                        newsList.filter { newsItem ->
                            categories.contains(newsItem.category.toString())
                        }.apply {
                            countUnreadMsg(this)
                        }
                    }
            }

    init {
        screenStateSubject.onNext(NewsState.Progress(0))
    }

    private fun loadNewsWithProgress(): Observable<List<NewsItem>> {
        return Observable.create<List<NewsItem>> { emitter ->
            try {
                for (i in 1..100 step 34) {
                    Thread.sleep(100)
                    screenStateSubject.onNext(NewsState.Progress(i))
                }
                val newsList = initialNewsList
                emitter.onNext(newsList)
                emitter.onComplete()
                screenStateSubject.onNext(NewsState.Done())
            } catch (e: Exception) {
                emitter.onError(e)
                screenStateSubject.onNext(NewsState.Done())
            }
        }.subscribeOn(Schedulers.io())
    }

    private fun countUnreadMsg(list: List<NewsItem>) {
        var unreadCount = list.size
        list.forEach { newsItem ->
            if (setOfReadNews.contains(newsItem)) unreadCount--
            unreadCountSubject.onNext(unreadCount)
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

    fun removeNewsItemFromFilter(category: String) =
        tmpSetOfFilteredCategoriesToSave.remove(category)

    fun saveChosenCategories() {
        setOfChosenCategories = tmpSetOfFilteredCategoriesToSave.toSet()
        sharedPref.edit().apply {
            putStringSet(CHOSEN_CATEGORIES_KEY, tmpSetOfFilteredCategoriesToSave)
            apply()
        }
        categoriesSubject.onNext(tmpSetOfFilteredCategoriesToSave.toSet())
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
