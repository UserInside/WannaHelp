package com.example.newscompose

import com.example.common.navigation.NavigationCommand

interface NewsNavigator {
    val toEvent: NavigationCommand
    val toNewsFilter: NavigationCommand
}