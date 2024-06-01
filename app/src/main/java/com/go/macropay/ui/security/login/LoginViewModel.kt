package com.go.macropay.ui.security.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.go.macropay.domain.repository.AuthRepository
import com.go.macropay.domain.usecase.FirebaseLoginUseCase
import com.go.macropay.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: FirebaseLoginUseCase
): ViewModel(){

    private val _email = mutableStateOf("")
    val email: State<String> get() = _email

    private val _password = mutableStateOf("")
    val password: State<String> get() = _password

    private val _login: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val login: LiveData<Resource<Boolean>> get() = _login

    private val _loginStatusFinal = mutableStateOf(false)
    val loginStatusFinal: State<Boolean> get() = _loginStatusFinal

    var loginStatus = false
        private set

    fun LoginUser(){
        viewModelScope.launch {
           loginStatus = loginUseCase(email = email.value, password = password.value)
            updateLoginStatus(loginStatus = loginStatus)
        }
    }

    fun updateLoginStatus(loginStatus: Boolean){
        _loginStatusFinal.value = loginStatus
    }

    fun updateEmailText(text: CharSequence?) {
        _email.value = text.toString()
    }

    fun updatePasswordText(text: CharSequence?) {
        _password.value = text.toString()
    }
}