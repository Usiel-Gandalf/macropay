package com.go.macropay.ui.security.login

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.go.macropay.domain.repository.AuthenticationState
import com.go.macropay.domain.usecase.BiometricAuthenticator
import com.go.macropay.domain.usecase.FirebaseLoginUseCase
import com.go.macropay.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.go.macropay.domain.repository.AuthenticationCallback
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: FirebaseLoginUseCase,
    private val biometricAuthenticator: BiometricAuthenticator
): ViewModel(){

    private val _email = mutableStateOf("")
    val email: State<String> get() = _email

    private val _password = mutableStateOf("")
    val password: State<String> get() = _password

    private val _login: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val login: LiveData<Resource<Boolean>> get() = _login

    private val _loginStatusFinal = mutableStateOf(false)
    val loginStatusFinal: State<Boolean> get() = _loginStatusFinal

    private val _biometricEnabled = MutableStateFlow(false)
    val biometricEnabled: StateFlow<Boolean> get() = _biometricEnabled

    private val _authenticationState = MutableStateFlow<AuthenticationState>(AuthenticationState.Idle)
    val authenticationState: StateFlow<AuthenticationState> get() = _authenticationState

    var loginStatus = false
        private set

    fun LoginUser(){
        viewModelScope.launch {
           loginStatus = loginUseCase(email = email.value, password = password.value)
            updateLoginStatus(loginStatus = loginStatus)
        }
    }

    fun checkIfPhoneItsAbleToBiometricsAuthenticator() {
        _biometricEnabled.value = biometricAuthenticator.isBiometricAvailable()

    }

    fun authenticate(activity: AppCompatActivity) {
        biometricAuthenticator.authenticate(
            activity,
            onSuccess = { _authenticationState.value = AuthenticationState.Success },
            onFailure = { _authenticationState.value = AuthenticationState.Failure },
            onError = { message -> _authenticationState.value = AuthenticationState.Error(message) }
        )
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