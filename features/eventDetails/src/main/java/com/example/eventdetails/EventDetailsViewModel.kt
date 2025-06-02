package com.example.eventdetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.common.models.NewsUiModel
import com.example.common.models.mapper.NewsMapper
import com.example.domain.interactors.NewsInteractor
import com.example.eventdetails.di.EventDetailsComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventDetailsViewModelFactory(
    private val eventDetailsComponent: EventDetailsComponent,
    private val eventId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return EventDetailsViewModel(
            eventDetailsComponent = eventDetailsComponent,
            eventId = eventId
        ) as T
    }
}

class EventDetailsViewModel(eventDetailsComponent: EventDetailsComponent, val eventId: Int) :
    ViewModel() {

    private val _eventDetails = MutableStateFlow<NewsUiModel>(
        NewsUiModel()
    )
    val eventDetails = _eventDetails.asStateFlow()

    @Inject
    lateinit var interactor: NewsInteractor

    init {
        eventDetailsComponent.inject(this)
        viewModelScope.launch {
            loadEventDetailsFromDB()
        }
    }

    suspend fun loadEventDetailsFromDB() {
        _eventDetails.value = NewsMapper.mapNewsDomainModelToUi(interactor.getEventById(eventId))
        Log.e("LOL VM", "vm name : ${eventDetails.value.name}")
    }
}