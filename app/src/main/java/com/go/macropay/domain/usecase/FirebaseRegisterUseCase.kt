package com.go.macropay.domain.usecase

import com.go.macropay.domain.repository.AuthRepository
import javax.inject.Inject

class FirebaseRegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): Boolean {
        return authRepository.register(email, password)
    }
}