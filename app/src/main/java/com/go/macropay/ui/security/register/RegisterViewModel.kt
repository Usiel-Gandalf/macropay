package com.go.macropay.ui.security.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.go.macropay.domain.usecase.FirebaseRegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseRegisterUseCase: FirebaseRegisterUseCase,
) : ViewModel() {

    private val _email = mutableStateOf("")
    val email: State<String> get() = _email

    private val _password = mutableStateOf("")
    val password: State<String> get() = _password

    private val _registerStatusFinal = mutableStateOf(false)
    val registerStatusFinal: State<Boolean> get() = _registerStatusFinal

    var registerStatus = false
        private set

    fun RegisterUser() {
        viewModelScope.launch {
            registerStatus = firebaseRegisterUseCase(email.value, password.value)
            updateRegisterStatus(registerStatus = registerStatus)
        }
    }

    fun updateRegisterStatus(registerStatus: Boolean) {
        _registerStatusFinal.value = registerStatus
    }

    fun updateEmailText(text: CharSequence?) {
        _email.value = text.toString()
    }

    fun updatePasswordText(text: CharSequence?) {
        _password.value = text.toString()
    }
}