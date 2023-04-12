package com.example.clothingsuggester.data.remove

import com.example.clothingsuggester.domain.model.NationalResponse

interface WeatherService {
    fun getCurrentWeatherStatue(
        onGetResponse: (NationalResponse) -> Unit, onGetFailure: (String) -> Unit
    )
}