package com.example.myapplication.repository

import com.example.myapplication.api.RetrofitClient
import com.example.myapplication.data.model.ApiResponse
import retrofit2.Call

class ApiRepository {
    fun getRates(): Call<ApiResponse> {
        return RetrofitClient.apiService.getRates()
    }
}