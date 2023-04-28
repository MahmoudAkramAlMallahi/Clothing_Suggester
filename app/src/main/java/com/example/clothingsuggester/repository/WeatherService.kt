package com.example.clothingsuggester.repository

import com.example.clothingsuggester.model.WeatherResponse
import io.reactivex.rxjava3.core.Single

interface WeatherService {
    fun getCurrentWeatherStatus(): Single<WeatherResponse>
}