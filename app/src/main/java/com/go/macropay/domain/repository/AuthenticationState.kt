package com.go.macropay.domain.repository

sealed class AuthenticationState {
    object Idle : AuthenticationState()
    object Success : AuthenticationState()
    object Failure : AuthenticationState()
    data class Error(val message: String) : AuthenticationState()
}
