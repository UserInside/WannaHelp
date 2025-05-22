package com.example.newscompose

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import com.example.common.compose.BaseComposeFragment
import com.example.common.utils.extensions.navigate
import com.example.newscompose.di.NewsComposeComponentViewModel
import com.example.newscompose.di.NewsComposeComponent
import javax.inject.Inject

class NewsComposeFragment : BaseComposeFragment() {

    @Inject
    lateinit var newsComposeComponent: NewsComposeComponent

    @Inject
    lateinit var navigator: NewsComposeNavigator

    override fun onAttach(context: Context) {
        ViewModelProvider(this)[NewsComposeComponentViewModel::class].newsComposeComponent.inject(this)
        super.onAttach(context)
    }

    @Composable
    override fun ScreenContent() {
        val factory = NewsViewModelFactory(newsComposeComponent, requireContext())
        NewsScreen(
            factory = factory,
            onEventClick = { navigate(navigator.toEvent) },
            onNavigate = { navigate(navigator.toNewsFilter) }
        )
    }
}