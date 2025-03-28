package com.example.wannahelp.searchScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.wannahelp.common.extentions.parseToList
import com.example.wannahelp.newsScreen.NewsItem
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit

class SearchScreenViewModel(application: Application) : AndroidViewModel(application) {

    var searchFieldText = BehaviorSubject.createDefault("")

    val fondsToShowList = arrayListOf<String>(
        "Фонд 1",
        "Фонд 2",
        "Фонд 3",
        "Фонд 4",
    )

    val eventsToShowList = arrayListOf(
        "Фонд 1",
        "Фонд 2",
        "Фонд 3",
        "Фонд 4",
    )

    val eventsOriginList =
        Json.parseToList<NewsItem>(application.applicationContext, "news.json") //filename

    val listToShow = searchFieldText.debounce(500, TimeUnit.MILLISECONDS).map { searchText ->
        eventsOriginList.filter { it.title.contains(searchText) }
    }.distinctUntilChanged()
}