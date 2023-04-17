package com.example.clothingsuggester.data.remote

import com.example.clothingsuggester.model.WeatherResponse
import java.io.IOException

interface WeatherService {
    fun getCurrentWeatherStatus(
        onGetCurrentWeatherStatusSuccess: (weatherResponse: WeatherResponse) -> Unit,
        onGetCurrentWeatherStatusFailure: (e: IOException) -> Unit
    )
}