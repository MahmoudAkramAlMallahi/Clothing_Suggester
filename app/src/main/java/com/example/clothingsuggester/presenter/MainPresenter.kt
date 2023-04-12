package com.example.clothingsuggester.presenter

import com.example.clothingsuggester.data.remove.WeatherServiceImpl
import com.example.clothingsuggester.domain.model.NationalResponse

class MainPresenter(private var iMainView: IMainView) {

    private val weatherServiceImpl = WeatherServiceImpl()

    fun getCurrentWeatherState() =
        weatherServiceImpl.getCurrentWeatherStatue(::onGetResponse, ::onGetFailure)


    private fun onGetResponse(weatherResponse: NationalResponse) =
        iMainView.onGetWeatherState(weatherResponse)


    private fun onGetFailure(message: String) = iMainView.onGetFailure(message)

}

interface IMainView {
    fun onGetWeatherState(weatherResponse: NationalResponse)
    fun onGetFailure(message: String)
}