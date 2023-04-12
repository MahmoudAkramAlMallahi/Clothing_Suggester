package com.example.clothingsuggester.ui

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.clothingsuggester.databinding.ActivityMainBinding
import com.example.clothingsuggester.domain.model.NationalResponse
import com.example.clothingsuggester.presenter.IMainView
import com.example.clothingsuggester.presenter.MainPresenter
import com.example.clothingsuggester.until.chooseOutfitImage
import com.example.clothingsuggester.until.getAllImageCrews
import com.example.clothingsuggester.until.showViewOnFailureStatue
import com.example.clothingsuggester.until.showViewOnResponseStatue
import com.orhanobut.hawk.Hawk


class MainActivity : AppCompatActivity(), IMainView {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val presenter = MainPresenter(this)
        presenter.getCurrentWeatherState()

        Hawk.init(this).build()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onGetWeatherState(weatherResponse: NationalResponse) {
        val iconDescription = weatherResponse.weather[0].icon
        val temp = weatherResponse.main.temp
        val imagesCrews = getAllImageCrews()
        val imageOutfit = chooseOutfitImage(imagesCrews, temp)

        Hawk.put(OUTFIT_IMAGE, imageOutfit)

        runOnUiThread {
            showViewOnResponseStatue(weatherResponse, iconDescription, imageOutfit, binding)
        }
    }


    override fun onGetFailure(message: String) {
        runOnUiThread {
            showViewOnFailureStatue(message, binding)
        }
    }

    companion object {
        const val OUTFIT_IMAGE = "outfit image"
    }
}