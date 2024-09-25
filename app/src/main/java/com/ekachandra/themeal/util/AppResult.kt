package com.ekachandra.themeal.util

sealed class AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>()
    data class Error(val message: String) : AppResult<Nothing>()
    data object Loading : AppResult<Nothing>()
    data object Empty : AppResult<Nothing>()
}