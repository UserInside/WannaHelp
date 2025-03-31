package com.example.wannahelp.searchScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.wannahelp.common.extentions.parseToList
import com.example.wannahelp.newsScreen.NewsItem
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit

const val FILE_NAME = "news.json"

class SearchScreenViewModel(application: Application) : AndroidViewModel(application) {
    val eventsOriginList = Json.parseToList<NewsItem>(application.applicationContext, FILE_NAME)

    var searchQuery: BehaviorSubject<String> = BehaviorSubject.create<String>()

    var searchResultObservable: Observable<SearchResult> =
        searchQuery.debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .flatMap { searchText ->
                if (searchText == "") {
                    Observable.just(SearchResult.NoInputMade)
                } else {
                    val filteredList =
                        eventsOriginList.filter { it.title.contains(searchText) }
                            .ifEmpty { emptyList() }
                    Observable.just(SearchResult.ResultToShow(filteredList))
                }
            }
}

sealed class SearchResult {
    object NoInputMade : SearchResult()

    class ResultToShow(val listToShow: List<NewsItem>) : SearchResult()
}
