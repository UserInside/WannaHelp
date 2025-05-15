package com.example.wannahelp.presentation.searchScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.extensions.parseToList
import com.example.domain.entities.NewsDomainModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

const val FILE_NAME = "news.json"

class SearchScreenViewModel(application: Application) : AndroidViewModel(application) {
    val eventsOriginList = Json.parseToList<NewsDomainModel>(application.applicationContext, FILE_NAME)

    var searchQuery: MutableStateFlow<String> = MutableStateFlow<String>("")

    var searchResultStateFlow = MutableStateFlow<SearchResult>(SearchResult.NoInputMade)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    fun updateSearchResult(newText: String) {
        searchQuery.value = newText.lowercase()
        viewModelScope.launch {
            searchQuery.debounce(500).distinctUntilChanged().flatMapConcat { searchText ->
                flow {
                    val result =
                        if (searchText == "") {
                            SearchResult.NoInputMade
                        } else {
                            val filteredList =
                                eventsOriginList.filter { it.name.lowercase().contains(searchText) }
                                    .ifEmpty { emptyList() }
                            SearchResult.ResultToShow(filteredList)
                        }
                    emit(result)
                }
            }.collect { result ->
                searchResultStateFlow.value = result
            }
        }
    }
}

sealed class SearchResult {
    object NoInputMade : SearchResult()

    class ResultToShow(val listToShow: List<NewsDomainModel>) : SearchResult()
}
