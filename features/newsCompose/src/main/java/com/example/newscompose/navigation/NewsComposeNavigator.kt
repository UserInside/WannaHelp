package com.example.newscompose.navigation

import com.example.common.navigation.NavigationCommand

interface NewsComposeNavigator {
    val toEvent: NavigationCommand
    val toNewsFilter: NavigationCommand
}