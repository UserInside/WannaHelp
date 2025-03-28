package com.example.wannahelp.searchScreen

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class SearchScreenViewModel : ViewModel() {

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

    val listToShow = searchFieldText.debounce(500, TimeUnit.MILLISECONDS).map{ searchText ->
        fondsToShowList.filter { it.contains(searchText) } as ArrayList<String>
    }
}