package com.go.macropay.ui.security.login

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.go.macropay.MainActivity_GeneratedInjector
import com.go.macropay.R
import com.go.macropay.domain.repository.AuthenticationState
import com.go.macropay.ui.components.TopAppBar
import com.go.macropay.ui.components.TopAppBarWithoutIcon
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    loginViewModel: LoginViewModel,
    onClicGoToRegister: () -> Unit,
    navigateToMain: () -> Unit,
) {
    val context = LocalContext.current
    val activity = context as AppCompatActivity
    val authenticationState by loginViewModel.authenticationState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBarWithoutIcon("LOGIN")
        }, content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(all = 8.dp)
            ) {
                BodyLogin(
                    email = loginViewModel.email.value,
                    updateEmailText = {
                        loginViewModel.updateEmailText(it)
                    },
                    password = loginViewModel.password.value,
                    updatePasswordText = {
                        loginViewModel.updatePasswordText(it)
                    },
                    onClicGoToRegister = {
                        onClicGoToRegister()
                    },
                    onClicTryLogin = {
                        loginViewModel.LoginUser()
                        if (loginViewModel.loginStatusFinal.value) {
                            navigateToMain()
                        } else {
                            Toast.makeText(
                                context,
                                "Algo salio mal, intente de nuevo.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    checkIfPhoneItsAbleToBiometricsAuthenticator = {
                        loginViewModel.checkIfPhoneItsAbleToBiometricsAuthenticator()
                        if (loginViewModel.biometricEnabled.value) {
                            loginViewModel.authenticate(activity)
                        } else {
                            Toast.makeText(context, "El dispositivo no admite datos biométricos", Toast.LENGTH_LONG).show()
                        }
                    }
                )
            }
        },

        contentColor = Color.White
    )

    LaunchedEffect(authenticationState) {
        when (authenticationState) {
            is AuthenticationState.Success -> {
                Toast.makeText(context, "Autenticación exitosa", Toast.LENGTH_LONG).show()
                navigateToMain()
            }
            is AuthenticationState.Failure -> {
                Toast.makeText(context, "Autenticación fallida", Toast.LENGTH_LONG).show()
            }
            is AuthenticationState.Error -> {
                Toast.makeText(context, (authenticationState as AuthenticationState.Error).message, Toast.LENGTH_LONG)
                    .show()
            }
            else -> Unit
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyLogin(
    email: String,
    updateEmailText: (email: String) -> Unit,
    password: String,
    updatePasswordText: (password: String) -> Unit,
    onClicGoToRegister: () -> Unit,
    onClicTryLogin: () -> Unit,
    checkIfPhoneItsAbleToBiometricsAuthenticator: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.avatar), // Reemplaza con tu recurso SVG
                    contentDescription = "Avatar", // Reemplaza con tu string resource
                    modifier = Modifier.size(200.dp) // Ajusta el tamaño según sea necesario
                )
            }

            //// EMAIL
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 3.dp, top = 3.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = email,
                    onValueChange = {
                        updateEmailText(it)
                    },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            //PASSWORD
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 3.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = password,
                    onValueChange = {
                        updatePasswordText(it)
                    },
                    label = { Text("Password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            //Boton inicio de sesion con usario y contrasenia
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 3.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        onClicTryLogin.invoke()
                    },
                    modifier = Modifier
                        .size(width = 300.dp, height = 60.dp)
                ) {
                    Text(text = "Iniciar Sesion", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }

            //Boton inicio de sesion con biometricos
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 3.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        checkIfPhoneItsAbleToBiometricsAuthenticator()
                    },
                    modifier = Modifier
                        .size(width = 300.dp, height = 60.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.huella),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Biometricos",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No tienes una cuenta?",
                    color = Color.Blue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .clickable {
                            onClicGoToRegister()
                        }
                )
            }
        }
    }
}