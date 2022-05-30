package com.lucyseven.clothmatchingservice.cloth.api

interface ClothRecommender {
    fun recommend(minTemp: Int, maxTemp: Int): List<Cloth>
}


enum class Cloth {
    //예시, 회의 후 확정 필요
    PADDING,COAT,SHIRT,SLACKS,JEANS,LONG_SLEEVE, SHORT_SLEEVE, SLEEVELESS, SHORTS, SKIRTS
}