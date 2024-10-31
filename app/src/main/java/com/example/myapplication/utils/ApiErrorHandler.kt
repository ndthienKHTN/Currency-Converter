package com.example.myapplication.utils

import android.util.Log
import retrofit2.Response

object ApiErrorHandler {
    fun <T> handleApiError(response: Response<T>) {
        if (!response.isSuccessful) {
            Log.e("ApiErrorHandler", "API call failed with error: ${response.errorBody()?.string()}")
        }
    }

    fun handleApiFailure(t: Throwable) {
        Log.e("ApiErrorHandler", "API call failed: ${t.message}")
    }
}