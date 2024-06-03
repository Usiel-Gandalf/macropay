package com.go.macropay.di.security

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.go.macropay.domain.usecase.BiometricAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executor

@Module
@InstallIn(SingletonComponent::class)
object BiometricModule {

    @Provides
    fun provideBiometricManager(@ApplicationContext context: Context): BiometricManager {
        return BiometricManager.from(context)
    }

    @Provides
    fun provideExecutor(@ApplicationContext context: Context): Executor {
        return ContextCompat.getMainExecutor(context)
    }

    @Provides
    fun provideBiometricPrompt(
        activity: AppCompatActivity,
        executor: Executor,
        callback: BiometricPrompt.AuthenticationCallback
    ): BiometricPrompt {
        return BiometricPrompt(activity, executor, callback)
    }
}