package com.go.macropay.domain.usecase

import com.go.macropay.domain.repository.AuthRepository
import com.go.macropay.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(email: String, password: String) : Boolean{
        println("Inicio mandando a llamar a firebase")
        return authRepository.login(email, password)
    }
}