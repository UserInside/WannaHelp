package com.example.eventdetails.di

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.common.Feature
import com.example.data.di.DataModule
import com.example.eventdetails.EventDetailsScreenFragment
import com.example.eventdetails.EventDetailsViewModel
import dagger.Component
import javax.inject.Singleton

@Feature
@Singleton
@Component(modules = [DataModule::class])
interface EventDetailsComponent {
    fun inject(vm: EventDetailsViewModel)
    fun inject(fragment: EventDetailsScreenFragment)

    @Component.Builder
    interface Builder {
        fun dataModule(module: DataModule): Builder
        fun build(): EventDetailsComponent
    }
}

class EventDetailsComponentViewModel(application: Application) : AndroidViewModel(application) {
    val eventDetailsComponent = DaggerEventDetailsComponent.builder()
        .dataModule(DataModule(application.applicationContext))
        .build()
}
