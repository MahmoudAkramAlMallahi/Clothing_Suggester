package com.example.clothingsuggester.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.clothingsuggester.data.local.LocalServiceImpl
import com.example.clothingsuggester.databinding.ActivityMainBinding
import com.example.clothingsuggester.model.WeatherResponse
import com.example.clothingsuggester.presenter.IMainView
import com.example.clothingsuggester.presenter.MainPresenter
import com.orhanobut.hawk.Hawk
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity(), IMainView {
    lateinit var binding: ActivityMainBinding
    lateinit var localServiceImpl: LocalServiceImpl
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val presenter = MainPresenter(this)
        presenter.getCurrentWeatherStatus()
        localServiceImpl = LocalServiceImpl()


        Hawk.init(this).build()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getCurrentWeatherStatusSuccess(weatherResponse: WeatherResponse) {
        val iconDescription = weatherResponse.weather[0].icon
        val temp = weatherResponse.main.temp
        val imageOutfit = localServiceImpl.chooseClothesAccordingWeatherNow(temp)

        localServiceImpl.saveImageInSharedPreferences(imageOutfit)

        runOnUiThread {
            showViewsInCurrentWeatherStatusSuccess(
                weatherResponse,
                iconDescription,
                imageOutfit,
                binding
            )
        }
    }


    override fun getCurrentWeatherStatusFailure(message: IOException) {
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

    @SuppressLint("SetTextI18n")
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