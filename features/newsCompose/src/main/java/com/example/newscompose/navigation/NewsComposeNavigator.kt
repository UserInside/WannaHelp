package com.example.newscompose.navigation

import com.example.common.navigation.NavigationCommand

interface NewsComposeNavigator {
    val toEventDetails: NavigationCommand
    val toNewsFilter: NavigationCommand
}