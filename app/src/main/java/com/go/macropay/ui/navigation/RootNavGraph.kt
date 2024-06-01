package com.go.macropay.ui.navigation

import androidx.compose.runtime.Composable
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
fun RootNavGraph(mainViewModel: MainViewModel,
                 detailViewModel: DetailViewModel,
                 loginViewModel: LoginViewModel,
                 registerViewModel: RegisterViewModel,
                 navController: NavHostController){

    NavHost(navController = navController,
        startDestination = NavigationViews.LoginView.route
    ){

        composable(NavigationViews.LoginView.route){
            LoginView(loginViewModel = loginViewModel, onClicGoToRegister = {
                navController.navigate(NavigationViews.RegisterView.route)
            },
            onLoginNavigateToMain = {
                navController.navigate(NavigationViews.MainView.route)
            })
        }

        composable(NavigationViews.RegisterView.route){
            RegisterView(registerViewModel = registerViewModel,
                onClicGoToLogin = {
                    navController.navigate(NavigationViews.LoginView.route){
                        popUpTo(NavigationViews.RegisterView.route){inclusive = true}
                    }
                },
                onLoginNavigateToMain = {
                    navController.navigate(NavigationViews.MainView.route)
                }
            )
        }

        composable(NavigationViews.MainView.route){
            MainView(mainViewModel = mainViewModel, navigateToMovieDetail = {
                detailViewModel.setComicIdForDetail(it)
                navController.navigate(NavigationViews.DetailView.route)
            })
        }

        composable(NavigationViews.DetailView.route){
            DetailView(detailViewModel = detailViewModel, navToBackView = {
                navController.navigate(NavigationViews.MainView.route) {
                    popUpTo(NavigationViews.DetailView.route) { inclusive = true }
                }
            })
        }
    }

}