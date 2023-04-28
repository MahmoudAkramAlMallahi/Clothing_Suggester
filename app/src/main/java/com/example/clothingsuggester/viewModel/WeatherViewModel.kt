package com.example.clothingsuggester.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clothingsuggester.model.WeatherResponse
import com.example.clothingsuggester.repository.WeatherRepository
import io.reactivex.rxjava3.core.Single

class WeatherViewModel : ViewModel() {

    private val weatherRepository = WeatherRepository()

    private val _weather = MutableLiveData<Single<WeatherResponse>>()
    val weather: LiveData<Single<WeatherResponse>>
        get() = _weather

    fun getCurrentWeatherStatus() {
        val result = weatherRepository.getCurrentWeatherStatus()
        _weather.postValue(result)
    }

}