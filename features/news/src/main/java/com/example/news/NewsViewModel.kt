package com.example.news

import android.content.Context
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.common.extensions.datastore
import com.example.domain.entities.Category
import com.example.domain.interactors.NewsInteractor
import com.example.news.di.NewsComponent
import com.example.common.models.NewsUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModelFactory(
    private val newsComponent: NewsComponent,
    private val context: Context,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsComponent, context) as T
    }
}

class NewsViewModel(
    newsComponent: NewsComponent,
    private val context: Context,
) : ViewModel() {
    private val initialListToShow = getFullCatList()

    val screenStateFlow = MutableStateFlow<NewsState>(NewsState.Progress)

    val listToShowStateFlow: MutableStateFlow<List<NewsUiModel>> =
        MutableStateFlow<List<NewsUiModel>>(emptyList())

    val unreadMsgCountStateFlow =
        MutableStateFlow<Int>(listToShowStateFlow.value.count { it.isRead == false })

    @Inject
    lateinit var interactor: NewsInteractor

    init {
        newsComponent.inject(this)
        viewModelScope.launch {
            loadNewsFromDB()
        }
    }

    suspend fun loadNewsFromDB() {
        val setOfChosenCategories = getChosenCategoriesFromDS().map { it.lowercase() }.toSet()
        screenStateFlow.value = NewsState.Progress

        val eventsList = interactor.getNewsByCategories(setOfChosenCategories)

        listToShowStateFlow.value = eventsList.map { NewsMapper.mapNewsDomainModelToUi(it)}
        unreadMsgCountStateFlow.value = eventsList.count { !it.isRead }

        screenStateFlow.value = NewsState.Done
    }

    private suspend fun getChosenCategoriesFromDS(): Set<String> {
        return context.datastore.data.map { preference ->
            preference[stringSetPreferencesKey(CHOSEN_CATEGORIES)] ?: initialListToShow
        }.first()
    }

    fun markNewsItemAsRead(newsItemId: Int) = viewModelScope.launch { interactor.markNewsItemAsRead(newsItemId) }

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
