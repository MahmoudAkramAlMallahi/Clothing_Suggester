package com.example.clothingsuggester.presenter

import com.example.clothingsuggester.data.remote.WeatherServiceImpl
import com.example.clothingsuggester.model.WeatherResponse
import java.io.IOException

class MainPresenter(private var iMainView: IMainView) {

    private val weatherServiceImpl = WeatherServiceImpl()

    fun getCurrentWeatherStatus() =
        weatherServiceImpl.getCurrentWeatherStatus(
            ::onGetCurrentWeatherStatusSuccess,
            ::onGetCurrentWeatherStatusFailure
        )


    private fun onGetCurrentWeatherStatusSuccess(weatherResponse: WeatherResponse) =
        iMainView.getCurrentWeatherStatusSuccess(weatherResponse)


    private fun onGetCurrentWeatherStatusFailure(message: IOException) =
        iMainView.getCurrentWeatherStatusFailure(message)

}