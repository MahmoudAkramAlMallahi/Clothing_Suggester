package com.example.clothingsuggester.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.clothingsuggester.data.local.LocalServiceImpl
import com.example.clothingsuggester.databinding.ActivityMainBinding
import com.example.clothingsuggester.model.WeatherResponse
import com.example.clothingsuggester.viewModel.WeatherViewModel
import com.orhanobut.hawk.Hawk
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var localServiceImpl: LocalServiceImpl
    private val viewModel: WeatherViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        localServiceImpl = LocalServiceImpl()
        Hawk.init(this).build()

        viewModel.getCurrentWeatherStatus()
        viewModel.weather.observe(this) {
            it.subscribeBy(
                onSuccess = ::getCurrentWeatherStatusSuccess,
                onError = ::getCurrentWeatherStatusFailure
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentWeatherStatusSuccess(weatherResponse: WeatherResponse) {
        val iconDescription = weatherResponse.weather[0].icon
        val temp = weatherResponse.main.temp
        val imageOutfit = localServiceImpl.chooseClothesAccordingWeatherNow(temp)

        localServiceImpl.saveImageInSharedPreferences(imageOutfit)

        showViewsInCurrentWeatherStatusSuccess(
            weatherResponse,
            iconDescription,
            imageOutfit,
            binding
        )

    }


    fun getCurrentWeatherStatusFailure(message: Throwable) {
        runOnUiThread {
            showViewsInCurrentWeatherStatusFailure(message.toString(), binding)
        }
    }

    private fun showViewsInCurrentWeatherStatusFailure(
        message: String,
        binding: ActivityMainBinding
    ) {
        binding.textTitle.visibility = View.GONE
        binding.textError.visibility = View.VISIBLE
        binding.imageError.visibility = View.VISIBLE
        binding.textError.text = message
        binding.progressLoading.visibility = View.GONE
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun showViewsInCurrentWeatherStatusSuccess(
        result: WeatherResponse,
        icon: String,
        imageOutfit: Int,
        binding: ActivityMainBinding
    ) {
        val iconDescription = "https://openweathermap.org/img/wn/$icon@2x.png"
        val temp = result.main.temp.toInt().toString()

        binding.textCityName.text = result.name
        binding.textDate.text = LocalDate
            .now()
            .format(DateTimeFormatter.ofPattern("dd MMMM YYYY"))
            .toString()
        binding.textDescription.text = result.weather[0].description
        binding.textTemp.text = "${temp}°C"
        Glide.with(binding.imageDescription.context).load(iconDescription)
            .into(binding.imageDescription)
        Glide.with(binding.imageOutfit.context).load(imageOutfit).into(binding.imageOutfit)
        binding.progressLoading.visibility = View.GONE
    }
}