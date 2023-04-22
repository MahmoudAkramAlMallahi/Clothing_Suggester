package com.example.clothingsuggester.data.local

interface LocalService {

    fun chooseClothesAccordingWeatherNow(temp: Double): Int
    fun saveImageInSharedPreferences(imageOutfit: Int)
}