package com.example.wannahelp.navigation

import com.example.common.navigation.NavigationCommand
import com.example.news.NewsNavigator
import com.example.wannahelp.R
import javax.inject.Inject

class NewsNavigatorImpl
    @Inject
    constructor() : NewsNavigator {
        override val toEvent = NavigationCommand(R.id.navigateToEventDetailsScreen)
        override val toNewsFilter = NavigationCommand(R.id.navigateToNewsFilterScreen)
    }
