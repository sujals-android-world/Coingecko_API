package com.example.coingeckoapi.domain.helper

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

fun Throwable.toUserFriendlyMessage(): String {
    return when (this) {
        is SocketTimeoutException -> {
            "Connection timed out. Please try again."
        }
        is IOException -> {
            // Jab internet na ho ya connection fail ho jaye
            "Please check your internet connection."
        }
        is HttpException -> {
            when (code()) {
                401 -> "Unauthorized access. Please log in again."
                404 -> "The requested resource was not found."
                429, 423 -> "Too many requests. Please try again after some time."
                500, 502, 503 -> "Server is busy. Please try again later."
                else -> "Something went wrong. (Error code: ${code()})"
            }
        }
        else -> {
            localizedMessage ?: "An unexpected error occurred."
        }
    }
}