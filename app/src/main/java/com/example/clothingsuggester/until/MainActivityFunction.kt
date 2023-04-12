package com.example.clothingsuggester.until

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.clothingsuggester.R
import com.example.clothingsuggester.databinding.ActivityMainBinding
import com.example.clothingsuggester.domain.model.NationalResponse
import com.example.clothingsuggester.ui.MainActivity
import com.orhanobut.hawk.Hawk
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("SetTextI18n")
@RequiresApi(Build.VERSION_CODES.O)
fun showViewOnResponseStatue(
    result: NationalResponse,
    icon: String,
    imageOutfit: Int,
    binding: ActivityMainBinding
) {
    val iconDescription = "https://openweathermap.org/img/wn/$icon@2x.png"
    val temp = result.main.temp.toInt().toString()

    binding.textCityName.text = result.name
    binding.textDate.text = LocalDate
        .now()
        .format(DateTimeFormatter.ofPattern("dd MMMM"))
        .toString()
    binding.textDescription.text = result.weather[0].description
    binding.textTemp.text = "${temp}°C"
    Glide.with(binding.imageDescription.context).load(iconDescription)
        .into(binding.imageDescription)
    Glide.with(binding.imageOutfit.context).load(imageOutfit).into(binding.imageOutfit)
    binding.progressLoading.visibility = View.GONE
}

fun showViewOnFailureStatue(message: String, binding: ActivityMainBinding) {
    binding.textTitle.visibility = View.GONE
    binding.textError.visibility = View.VISIBLE
    binding.imageError.visibility = View.VISIBLE
    binding.textError.text = message
    binding.progressLoading.visibility = View.GONE
}

fun chooseOutfitImage(imageOutfit: List<List<Int>>, temp: Double): Int {
    return if (temp < 15.0) {
        imageOutfit[0].filter { it != Hawk.get(MainActivity.OUTFIT_IMAGE) }.random()
    } else if (temp in 15.0..25.0) {
        imageOutfit[1].filter { it != Hawk.get(MainActivity.OUTFIT_IMAGE) }.random()
    } else {
        imageOutfit[2].filter { it != Hawk.get(MainActivity.OUTFIT_IMAGE) }.random()
    }
}

private fun intiImageCrews(): List<List<Int>> {
    val autumnClothes = listOf(
        R.drawable.autumn_clothes_1,
        R.drawable.autumn_clothes_2,
        R.drawable.autumn_clothes_3
    )

    val summerClothes = listOf(
        R.drawable.summer_clothes_1,
        R.drawable.summer_clothes_2,
        R.drawable.summer_clothes_3
    )

    val winterClothes = listOf(
        R.drawable.witerr_clothes_1,
        R.drawable.witerr_clothes_2,
        R.drawable.witerr_clothes_3
    )

    return listOf(winterClothes, autumnClothes, summerClothes)
}

fun getAllImageCrews() = intiImageCrews()