package com.example.clothingsuggester.data.remove

import com.example.clothingsuggester.domain.model.NationalResponse
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class WeatherServiceImpl() : WeatherService {

    private val client = OkHttpClient()
    private fun HttpUrlBuilder() = HttpUrl.Builder()
        .scheme(SCHEME)
        .host(HOST)
        .addPathSegment(PATH)
        .addQueryParameter(UNITS, UNITS_VALUE)
        .addQueryParameter(APPID, APPID_VALUE)
        .addQueryParameter(LON, LON_VALUE)
        .addQueryParameter(LAT, LAT_VALUE)
        .build()

    override fun getCurrentWeatherStatue(
        onGetResponse: (NationalResponse) -> Unit, onGetFailure: (String) -> Unit
    ) {

        val url = HttpUrlBuilder()
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val message = e.message.toString()
                onGetFailure(message)
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val result = Gson().fromJson(body, NationalResponse::class.java)
                onGetResponse(result)
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
        private const val PATH = "/data/2.5/weather"
        private const val UNITS_VALUE = "metric"
        private const val APPID_VALUE = "8f670e13c976d1aeac0cfc6c2b3e6db9"
        private const val LON_VALUE = "34.4667"
        private const val LAT_VALUE = "31.5"

    }
}