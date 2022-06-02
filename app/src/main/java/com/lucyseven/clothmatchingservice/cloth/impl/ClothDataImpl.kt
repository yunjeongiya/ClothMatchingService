package com.lucyseven.clothmatchingservice.cloth.impl

import com.lucyseven.clothmatchingservice.cloth.api.Cloth
import com.lucyseven.clothmatchingservice.cloth.api.ClothRecommender

class ClothDataImpl : ClothRecommender {

    override fun recommend(minTemp: Int, maxTemp: Int): List<Cloth> =

        when ((minTemp + maxTemp) / 2) {
            in -70..-10 ->
                arrayListOf(
                    Cloth.LONG_PADDING,
                    Cloth.FLEECE_PANTS
                )
            in -9..4 ->
                arrayListOf(
                    Cloth.LONG_PADDING,
                    Cloth.PADDING,
                    Cloth.WINTER_COAT,
                    Cloth.SCARF,
                    Cloth.GLOVES,
                    Cloth.FLEECE_PANTS
                )
            in 5..8 ->
                arrayListOf(
                    Cloth.WINTER_COAT,
                    Cloth.LIGHT_PADDING,
                    Cloth.LEATHER_JACKET,
                    Cloth.LONG_UNDERWEAR,
                )
            in 9..11 ->
                arrayListOf(
                    Cloth.LIGHT_PADDING,
                    Cloth.LEATHER_JACKET,
                    Cloth.TRENCH_COAT,
                    Cloth.JUMPER,
                    Cloth.THIN_COAT,
                )
            in 12..15 ->
                arrayListOf(
                    Cloth.THIN_COAT,
                    Cloth.JUMPER,
                    Cloth.JACKET,
                    Cloth.THICK_CARDIGAN,
                    Cloth.THICK_NEAT,
                )
            in 15..19 ->
                arrayListOf(
                    Cloth.NEAT,
                    Cloth.CARDIGAN,
                    Cloth.HOOD,
                    Cloth.MANTOMAN,
                    Cloth.THIN_JACKET,
                    Cloth.COTTON_PANTS,
                    Cloth.SLACKS
                )
            in 20..22 ->
                arrayListOf(
                    Cloth.LONG_T_SHIRT,
                    Cloth.HOOD,
                    Cloth.SHIRT,
                    Cloth.ONE_PIECE
                )
            in 23..26 ->
                arrayListOf(
                    Cloth.THIN_LONG_T_SHIRT,
                    Cloth.THIN_SHIRT,
                    Cloth.T_SHIRT,
                    Cloth.SHORT_PANTS
                )
            else ->
                arrayListOf(
                    Cloth.SLEEVELESS,
                    Cloth.LINEN,
                    Cloth.SHORT_PANTS,
                    Cloth.T_SHIRT
                )
        }
}
