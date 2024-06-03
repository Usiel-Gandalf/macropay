package com.go.macropay.domain.repository

import androidx.biometric.BiometricPrompt

interface AuthenticationCallback {
    fun onError(errorCode: Int, errString: String)
    fun onSuccess(result: BiometricPrompt.AuthenticationResult)
    fun onFailure()
}