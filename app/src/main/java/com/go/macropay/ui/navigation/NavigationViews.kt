package com.go.macropay.ui.navigation

sealed class NavigationViews(val route: String) {
    object LoginView : NavigationViews(route = "LOGIN")
    object RegisterView : NavigationViews(route = "REGISTER")
    object MainView : NavigationViews(route = "MAIN")
    object DetailView : NavigationViews(route = "DETAIL")
}