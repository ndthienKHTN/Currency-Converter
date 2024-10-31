// File: app/src/test/java/com/example/myapplication/viewmodel/MainViewModelTest.kt

package com.example.myapplication.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myapplication.data.model.ApiResponse
import com.example.myapplication.repository.ApiRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var call: Call<ApiResponse>

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        mainViewModel = MainViewModel()
        // Inject the mock repository into the ViewModel
        mainViewModel.apiRepository = apiRepository
    }

    @Test
    fun `fetchRates success updates rates and networkError`() = runTest {
        val rates = mapOf("USD" to 1.0, "EUR" to 0.85)
        val apiResponse = ApiResponse(true, 1234567890L, "USD", "2023-10-01", rates)
        val response = Response.success(apiResponse)

        `when`(apiRepository.getRates()).thenReturn(call)
        doAnswer {
            val callback: Callback<ApiResponse> = it.getArgument(0)
            callback.onResponse(call, response)
        }.`when`(call).enqueue(any())

        mainViewModel.fetchRates()

        assertEquals(rates, mainViewModel.rates.first())
        assertEquals(false, mainViewModel.networkError.value)
    }

    @Test
    fun `fetchRates failure updates networkError`() = runTest {
        `when`(apiRepository.getRates()).thenReturn(call)
        doAnswer {
            val callback: Callback<ApiResponse> = it.getArgument(0)
            callback.onFailure(call, Throwable("Network error"))
        }.`when`(call).enqueue(any())

        mainViewModel.fetchRates()

        assertEquals(true, mainViewModel.networkError.value)
    }
}