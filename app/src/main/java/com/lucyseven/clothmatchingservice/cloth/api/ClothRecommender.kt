package com.lucyseven.clothmatchingservice.cloth.api

interface ClothRecommender {
    fun recommend(minTemp: Int, maxTemp: Int): List<Cloth>
}

enum class Cloth {
    LONG_PADDING,
    PADDING,
    SCARF,
    GLOVES,
    FLEECE_PANTS,
    WINTER_COAT,
    LIGHT_PADDING,
    LEATHER_JACKET,
    LONG_UNDERWEAR,
    TRENCH_COAT,
    JUMPER,
    THIN_COAT,
    JACKET,
    THICK_CARDIGAN,
    THICK_NEAT,
    NEAT,
    CARDIGAN,
    HOOD,
    MANTOMAN,
    THIN_JACKET,
    COTTON_PANTS,
    SLACKS,
    LONG_T_SHIRT,
    SHIRT,
    ONE_PIECE,
    THIN_LONG_T_SHIRT,
    THIN_SHIRT,
    T_SHIRT,
    SHORT_PANTS,
    SLEEVELESS,
    LINEN
}

