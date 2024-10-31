package com.example.myapplication.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.model.ApiResponse
import com.example.myapplication.repository.ApiRepository
import com.example.myapplication.utils.ApiErrorHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel() : ViewModel() {
    private val _rates = MutableStateFlow<Map<String, Double>>(emptyMap())
    val rates: StateFlow<Map<String, Double>> get() = _rates
    var networkError = mutableStateOf(false)
        private set


    var apiRepository = ApiRepository()

    fun fetchRates() {
        apiRepository.getRates().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    _rates.value = response.body()?.rates ?: emptyMap()
                    networkError.value = false
                    Log.d("MainViewModel", "Data fetched successfully: ${response.body()}")
                } else {
                    ApiErrorHandler.handleApiError(response)
                    networkError.value = true
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                ApiErrorHandler.handleApiFailure(t)
                networkError.value = true
            }
        })
    }
}
