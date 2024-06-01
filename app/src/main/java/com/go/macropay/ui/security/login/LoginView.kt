package com.go.macropay.ui.security.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.go.macropay.R
import com.go.macropay.ui.components.TopAppBar
import com.go.macropay.ui.components.TopAppBarWithoutIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    loginViewModel: LoginViewModel,
    onClicGoToRegister: () -> Unit,
    onLoginNavigateToMain: () -> Unit
) {
    val context = LocalContext.current

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
                        onLoginNavigateToMain()
                    }else{
                        Toast.makeText(context, "Algo salio mal, intente de nuevo.", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    })
}
@Composable
fun printToast(){
    Toast.makeText(LocalContext.current, "Algo salio mal, intente de nuevo", Toast.LENGTH_SHORT).show()
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