package com.example.myapplication.data

import com.example.myapplication.data.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
        @GET("latest?access_key=0df309d72385bf0f96d73a4b191628ce")
    fun getRates(): Call<ApiResponse>
}