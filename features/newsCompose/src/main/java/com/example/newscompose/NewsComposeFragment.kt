package com.example.newscompose

import android.content.Context
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import com.example.common.compose.BaseComposeFragment
import com.example.common.utils.extensions.navigate
import com.example.newscompose.di.NewsComposeComponentViewModel
import com.example.newscompose.di.NewsComposeComponent
import com.example.newscompose.navigation.NewsComposeNavigator
import com.example.newscompose.screen.NewsScreen
import javax.inject.Inject

class NewsComposeFragment : BaseComposeFragment() {

    @Inject
    lateinit var newsComposeComponent: NewsComposeComponent

    @Inject
    lateinit var navigator: NewsComposeNavigator

    override fun onAttach(context: Context) {
        ViewModelProvider(this)[NewsComposeComponentViewModel::class].newsComposeComponent.inject(
            this
        )
        super.onAttach(context)
    }

    @Composable
    override fun ScreenContent() {
        val factory = NewsViewModelFactory(newsComposeComponent, requireContext())
        NewsScreen(
            factory = factory,
            onEventClick = { newsItem ->
                navigator.toEvent.args = Bundle().apply {
                    putSerializable(OPEN_EVENT_KEY, newsItem)
                }
                navigate(navigator.toEvent)
            },
            onNavigate = { navigate(navigator.toNewsFilter) }
        )
    }

    companion object {
        private const val OPEN_EVENT_KEY = "event"
    }
}