package com.example.clothingsuggester.data.local

import com.example.clothingsuggester.R
import com.orhanobut.hawk.Hawk

class LocalServiceImpl : LocalService {


    override fun chooseClothesAccordingWeatherNow(temp: Double): Int {
        val imagesCrews = intiImageCrews()
        return if (temp < 15.0) {
            imagesCrews[0].filter { it != Hawk.get(OUTFIT_IMAGE) }.random()
        } else if (temp in 15.0..25.0) {
            imagesCrews[1].filter { it != Hawk.get(OUTFIT_IMAGE) }.random()
        } else {
            imagesCrews[2].filter { it != Hawk.get(OUTFIT_IMAGE) }.random()
        }
    }

    override fun saveImageInSharedPreferences(imageOutfit: Int) {
        Hawk.put(OUTFIT_IMAGE, imageOutfit)
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

    companion object {
        const val OUTFIT_IMAGE = "outfit image"
    }
}