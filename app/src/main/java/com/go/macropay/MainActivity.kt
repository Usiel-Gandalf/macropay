package com.go.macropay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.go.macropay.ui.detail.DetailViewModel
import com.go.macropay.ui.main.MainViewModel
import com.go.macropay.ui.navigation.RootNavGraph
import com.go.macropay.ui.security.login.LoginViewModel
import com.go.macropay.ui.security.register.RegisterViewModel
import com.go.macropay.ui.theme.MacropayTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MacropayTheme {
                val viewModel: MainViewModel = hiltViewModel()
                val detailViewModel: DetailViewModel = hiltViewModel()
                val registerViewModel: RegisterViewModel = hiltViewModel()
                val loginViewModel: LoginViewModel = hiltViewModel()
                val navController = rememberNavController()

                RootNavGraph(
                    mainViewModel = viewModel,
                    detailViewModel = detailViewModel,
                    loginViewModel = loginViewModel,
                    registerViewModel = registerViewModel,
                    navController = navController
                )
            }
        }
    }
}
