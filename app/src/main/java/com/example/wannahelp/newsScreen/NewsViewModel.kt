package com.example.wannahelp.newsScreen

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
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
    private var sharedPref: SharedPreferences =
        application.applicationContext.getSharedPreferences(CHOSEN_CATEGORIES_KEY, MODE_PRIVATE)

    private val screenStateSubject = BehaviorSubject.createDefault<NewsState>(NewsState.Done())
    val screenStateObservable: Observable<NewsState> = screenStateSubject

    private val unreadCountSubject: BehaviorSubject<Int> = BehaviorSubject.createDefault(0)
    val unreadCountObs: Observable<Int> = unreadCountSubject

    private var setOfChosenCategories =
        sharedPref.getStringSet(CHOSEN_CATEGORIES_KEY, null) ?: emptySet()

    private var categoriesSubject = BehaviorSubject.createDefault(setOfChosenCategories)

    val listToShow: Observable<List<NewsItem>> =
        categoriesSubject
            .switchMap { categories ->
                loadNewsWithProgress()
                    .map { newsList ->
                        newsList.filter { newsItem ->
                            categories.contains(newsItem.category.toString())
                        }
                    }
            }

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        screenStateSubject.onNext(NewsState.Progress(0))
    }

    private fun loadNewsWithProgress(): Observable<List<NewsItem>> {
        return Observable.create<List<NewsItem>> { emitter ->
            try {
                for (i in 1..100 step 34) {
                    Thread.sleep(100)
                    screenStateSubject.onNext(NewsState.Progress(i))
                }
                val newsList = getNewsList()
                unreadCountSubject.onNext(newsList.size)
                emitter.onNext(newsList)
                emitter.onComplete()
                screenStateSubject.onNext(NewsState.Done())
            } catch (e: Exception) {
                emitter.onError(e)
                screenStateSubject.onNext(NewsState.Done())
            }
        }.subscribeOn(Schedulers.io())
    }

    @SuppressLint("CheckResult")
    private fun getNewsList(): List<NewsItem> {
        return Json.parseToList<NewsItem>(
            getApplication<Application>().applicationContext,
            NEWS_FILE_NAME,
        )
    }

    fun decreaseCount() {
        val currentCount = unreadCountSubject.value
        unreadCountSubject.onNext(currentCount?.minus(1) ?: 0)
    }

    // filter screen below
    // изменить источник
    val initialFilterCategoryList =
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

    val listOfCategoryFiltersToShow =
        initialFilterCategoryList.map { list ->
            list.map { item ->
                if (setOfChosenCategories.contains(item.category.toString())) {
                    item.copy(isChecked = true)
                } else {
                    item.copy(isChecked = false)
                }
            }
        }

    var tmpSetOfFilteredCategoriesToSave = setOfChosenCategories.toMutableSet()

    fun addNewsItemToFilter(category: String) = tmpSetOfFilteredCategoriesToSave.add(category)

    fun removeNewsItemFromFilter(category: String) = tmpSetOfFilteredCategoriesToSave.remove(category)

    fun saveChosenCategories() {
        setOfChosenCategories = tmpSetOfFilteredCategoriesToSave.toSet()
        sharedPref.edit().apply {
            putStringSet(CHOSEN_CATEGORIES_KEY, tmpSetOfFilteredCategoriesToSave)
            apply()
        }
        categoriesSubject.onNext(tmpSetOfFilteredCategoriesToSave.toSet())
    }

    companion object {
        private const val CHOSEN_CATEGORIES_KEY = "chosenCategories"
        private const val NEWS_FILE_NAME = "news.json"
        const val NEWS_FILE_NAME_KEY = "news"
    }
}

sealed class NewsState {
    class Progress(val progress: Int) : NewsState()

    class Done() : NewsState()
}
