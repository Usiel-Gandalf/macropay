package com.go.macropay.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.go.macropay.ui.detail.DetailView
import com.go.macropay.ui.detail.DetailViewModel
import com.go.macropay.ui.main.MainView
import com.go.macropay.ui.main.MainViewModel
import com.go.macropay.ui.security.login.LoginView
import com.go.macropay.ui.security.login.LoginViewModel
import com.go.macropay.ui.security.register.RegisterView
import com.go.macropay.ui.security.register.RegisterViewModel

@Composable
fun RootNavGraph(
    navController: NavHostController,
) {
    val detailViewModel: DetailViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = NavigationViews.LoginView.route
    ) {

        composable(NavigationViews.LoginView.route) {
            val loginViewModel: LoginViewModel = hiltViewModel()

            LoginView(loginViewModel = loginViewModel, onClicGoToRegister = {
                navController.navigate(NavigationViews.RegisterView.route)
            },
                navigateToMain = {
                    navController.navigate(NavigationViews.MainView.route)
                })
        }

        composable(NavigationViews.RegisterView.route) {
            val registerViewModel: RegisterViewModel = hiltViewModel()
            RegisterView(registerViewModel = registerViewModel,
                onClicGoToLogin = {
                    navController.navigate(NavigationViews.LoginView.route) {
                        popUpTo(NavigationViews.RegisterView.route) { inclusive = true }
                    }
                },
                onLoginNavigateToMain = {
                    navController.navigate(NavigationViews.MainView.route)
                }
            )
        }

        composable(NavigationViews.MainView.route) {
            val mainViewModel: MainViewModel = hiltViewModel()
            MainView(mainViewModel = mainViewModel, navigateToMovieDetail = {
                detailViewModel.setComicIdForDetail(it)
                navController.navigate(NavigationViews.DetailView.route)
            },
                logout = {
                    navController.navigate(NavigationViews.LoginView.route) {
                        popUpTo(NavigationViews.MainView.route) { inclusive = true }
                    }
                })
        }

        composable(NavigationViews.DetailView.route) {
            DetailView(detailViewModel = detailViewModel, navToBackView = {
                navController.navigate(NavigationViews.MainView.route) {
                    popUpTo(NavigationViews.DetailView.route) { inclusive = true }
                }
            },
                logout = {
                    navController.navigate(NavigationViews.LoginView.route) {
                        popUpTo(NavigationViews.DetailView.route) { inclusive = true }
                    }
                })
        }
    }
}