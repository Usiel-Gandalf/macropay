package com.go.macropay.data.remote

import com.go.macropay.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : AuthRepository {
    override suspend fun login(email: String, password: String): Boolean {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            authResult.user != null
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun register(email: String, password: String): Boolean {
        return try {
            var isSuccesful = false

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                isSuccesful = it.isSuccessful
            }
            isSuccesful
        } catch (e: Exception) {
            false
        }
    }
}