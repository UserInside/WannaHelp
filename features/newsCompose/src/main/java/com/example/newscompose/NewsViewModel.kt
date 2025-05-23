package com.example.newscompose

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.common.models.NewsUiModel
import com.example.common.utils.extensions.datastore
import com.example.domain.entities.Category
import com.example.domain.interactors.NewsInteractor
import com.example.newscompose.di.NewsComposeComponent
import com.example.newscompose.mapper.NewsMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModelFactory(
    private val newsComponent: NewsComposeComponent,
    private val context: Context,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsComponent, context) as T
    }
}

data class NewsScreenState(
    val lts: MutableStateFlow<List<NewsUiModel>> = MutableStateFlow<List<NewsUiModel>>(emptyList()),
    val unReadMsgCount: Int = 0,
    val state: NewsState = NewsState.Done,
)

class NewsViewModel(
    newsComponent: NewsComposeComponent,
    private val context: Context,
) : ViewModel() {
    private val initialListToShow = getFullCategoriesList()

    var state by mutableStateOf(NewsScreenState())
        private set

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
        this.state = state.copy(state = NewsState.Progress)

        val eventsList = interactor.getNewsByCategories(setOfChosenCategories)

        state = state.copy(lts = MutableStateFlow<List<NewsUiModel>>(eventsList.map {
            NewsMapper.mapNewsDomainModelToUi(it)
        }))
        state = state.copy(unReadMsgCount = eventsList.count { !it.isRead })

        this.state = state.copy(state = NewsState.Done)
    }

    private suspend fun getChosenCategoriesFromDS(): Set<String> {
        return context.datastore.data.map { preference ->
            preference[stringSetPreferencesKey(CHOSEN_CATEGORIES)] ?: initialListToShow
        }.first()
    }

    fun onEvent(event: NewsScreenEvent) {
        when (event) {
            is NewsScreenEvent.OnEventClickedEvent -> {
                markNewsItemAsRead(event.newsItem.id)
            }
        }
    }

    private fun markNewsItemAsRead(newsItemId: Int) =
        viewModelScope.launch { interactor.markNewsItemAsRead(newsItemId) }

    private fun getFullCategoriesList() = setOf(
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

sealed class NewsScreenEvent {
    data class OnEventClickedEvent(val newsItem: NewsUiModel) : NewsScreenEvent()
}

sealed class NewsState {
    object Progress : NewsState()

    object Done : NewsState()
}
