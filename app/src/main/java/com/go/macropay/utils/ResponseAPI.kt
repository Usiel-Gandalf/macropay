package com.go.macropay.utils

sealed class ResponseAPI<T> {
    data class Success<T>(val data: T): ResponseAPI<T>()
    data class OnFailure<T>(val message: String): ResponseAPI<T>()
}