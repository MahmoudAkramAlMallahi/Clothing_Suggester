package com.example.clothingsuggester.data.remote

import com.example.clothingsuggester.model.WeatherResponse
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class WeatherServiceImpl() : WeatherService {

    private val client = OkHttpClient()
    override fun getCurrentWeatherStatus(
        onGetCurrentWeatherStatusSuccess: (weatherResponse: WeatherResponse) -> Unit,
        onGetCurrentWeatherStatusFailure: (e: IOException) -> Unit
    ) {

        val url = HttpUrl.Builder()
            .scheme(SCHEME)
            .host(HOST)
            .addPathSegment(PATH_DATA)
            .addPathSegment(PATH_VERSION)
            .addPathSegment(PATH_WEATHER)
            .addQueryParameter(UNITS, UNITS_VALUE)
            .addQueryParameter(APPID, APPID_VALUE)
            .addQueryParameter(LON, LON_VALUE)
            .addQueryParameter(LAT, LAT_VALUE)
            .build()

        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onGetCurrentWeatherStatusFailure(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val weatherResponseResult = Gson().fromJson(body, WeatherResponse::class.java)
                onGetCurrentWeatherStatusSuccess(weatherResponseResult)
            }
        })
    }

    companion object {
        private const val UNITS = "units"
        private const val APPID = "appid"
        private const val LON = "lon"
        private const val LAT = "lat"
        private const val SCHEME = "https"
        private const val HOST = "api.openweathermap.org"
        private const val PATH_DATA = "data"
        private const val PATH_VERSION = "2.5"
        private const val PATH_WEATHER = "weather"
        private const val UNITS_VALUE = "metric"
        private const val APPID_VALUE = "8f670e13c976d1aeac0cfc6c2b3e6db9"
        private const val LON_VALUE = "34.4667"
        private const val LAT_VALUE = "31.5"

    }
}