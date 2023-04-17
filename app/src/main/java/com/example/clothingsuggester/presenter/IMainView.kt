package com.example.clothingsuggester.presenter

import com.example.clothingsuggester.model.WeatherResponse
import java.io.IOException

interface IMainView {
    fun getCurrentWeatherStatusSuccess(weatherResponse: WeatherResponse)
    fun getCurrentWeatherStatusFailure(e: IOException)
}