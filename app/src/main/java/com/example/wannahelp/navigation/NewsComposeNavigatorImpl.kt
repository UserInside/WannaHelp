package com.example.wannahelp.navigation

import com.example.common.navigation.NavigationCommand
import com.example.newscompose.NewsComposeNavigator
import com.example.wannahelp.R
import javax.inject.Inject

class NewsComposeNavigatorImpl
    @Inject
    constructor() : NewsComposeNavigator {
        override val toEvent = NavigationCommand(R.id.navigateToEventDetailsScreen)
        override val toNewsFilter = NavigationCommand(R.id.navigateToNewsFilterScreen)
    }
