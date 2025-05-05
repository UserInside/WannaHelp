package com.example.wannahelp.presentation.newsScreen

import android.annotation.SuppressLint
import android.app.Application
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wannahelp.MainApp
import com.example.wannahelp.common.Category
import com.example.wannahelp.common.extentions.datastore
import com.example.wannahelp.domain.entities.NewsItem
import com.example.wannahelp.domain.interactors.NewsInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    val ctx = application.applicationContext

    private val initialListToShow = getFullCatList()

    val screenStateFlow = MutableStateFlow<NewsState>(NewsState.Progress)

    val listToShowStateFlow: MutableStateFlow<List<NewsItem>> =
        MutableStateFlow<List<NewsItem>>(emptyList())

    val unreadMsgCountStateFlow =
        MutableStateFlow<Int>(listToShowStateFlow.value.count { it.isRead == false })

    @Inject
    lateinit var interactor: NewsInteractor

    init {
        MainApp.appComponent.inject(this)
        viewModelScope.launch {
            loadNewsFromDB()
        }
    }

    suspend fun loadNewsFromDB() {
        val setOfChosenCategories = getChosenCategoriesFromDS().map { it.lowercase() }.toSet()
        screenStateFlow.value = NewsState.Progress

        val eventsList = interactor.getNewsByCategories(setOfChosenCategories)

        listToShowStateFlow.value = eventsList
        unreadMsgCountStateFlow.value = eventsList.count { !it.isRead }

        screenStateFlow.value = NewsState.Done
    }

    private suspend fun getChosenCategoriesFromDS(): Set<String> {
        return ctx.datastore.data.map { preference ->
            preference[stringSetPreferencesKey(CHOSEN_CATEGORIES)] ?: initialListToShow
        }.first()
    }

    fun markNewsItemAsRead(newsItemId: Int) =
        viewModelScope.launch { interactor.markNewsItemAsRead(newsItemId) }

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
