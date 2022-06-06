package com.lucyseven.clothmatchingservice.cloth.api

interface ClothRecommender {
    fun recommend(minTemp: Int, maxTemp: Int): List<Cloth>
}

data class Cloth(val iconId: Int, val name: String)
