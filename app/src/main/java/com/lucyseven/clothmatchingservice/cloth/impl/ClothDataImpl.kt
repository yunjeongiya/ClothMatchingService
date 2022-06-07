package com.lucyseven.clothmatchingservice.cloth.impl

import com.lucyseven.clothmatchingservice.R
import com.lucyseven.clothmatchingservice.cloth.api.Cloth
import com.lucyseven.clothmatchingservice.cloth.api.ClothRecommender

class ClothDataImpl : ClothRecommender {

    override fun recommend(minTemp: Int, maxTemp: Int): List<Cloth> =

        when ((maxTemp * 3 + minTemp) / 4) {
            in -70..-10 ->
                arrayListOf(
                    Cloth(R.drawable.winter_padding, "롱패딩 / 겨울패딩"),
                    Cloth(R.drawable.fleece_pants, "내복"),
                    Cloth(R.drawable.winter_pants, "겨울면바지"),
                    Cloth(R.drawable.gloves, "장갑"),
                    Cloth(R.drawable.winter_scarf, "목도리")
                )
            in -9..0 ->
                arrayListOf(
                    Cloth(R.drawable.winter_padding, "롱패딩 / 겨울패딩"),
                    Cloth(R.drawable.padding, "패딩"),
                    Cloth(R.drawable.winter_coat, "겨울코트"),
                    Cloth(R.drawable.fleece_pants, "내복"),
                    Cloth(R.drawable.winter_pants, "겨울면바지"),
                    Cloth(R.drawable.gloves, "장갑"),
                    Cloth(R.drawable.winter_scarf, "목도리")
                )
            in 0..4 ->
                arrayListOf(
                    Cloth(R.drawable.winter_coat, "겨울코트"),
                    Cloth(R.drawable.padding, "패딩"),
                    Cloth(R.drawable.leather_jacket, "가죽자켓"),
                    Cloth(R.drawable.jacket, "자켓"),
                    Cloth(R.drawable.sweater, "스웨터"),
                    Cloth(R.drawable.winter_pants, "겨울면바지")
                )
            in 4..9 ->
                arrayListOf(
                    Cloth(R.drawable.padding, "가벼운패딩"),
                    Cloth(R.drawable.leather_jacket, "가죽자켓"),
                    Cloth(R.drawable.thin_coat, "트렌치코트"),
                    Cloth(R.drawable.thin_coat, "얇은 코트"),
                    Cloth(R.drawable.leather_jacket, "가죽자켓"),
                    Cloth(R.drawable.jacket, "자켓"),
                    Cloth(R.drawable.sweater, "스웨터"),
                    Cloth(R.drawable.cotton_pants, "면바지"),
                    Cloth(R.drawable.jeans, "청바지")
                )
            in 9..13 ->
                arrayListOf(
                    Cloth(R.drawable.thin_coat, "트렌치코트"),
                    Cloth(R.drawable.thin_coat, "얇은 코트"),
                    Cloth(R.drawable.leather_jacket, "가죽자켓"),
                    Cloth(R.drawable.jacket, "자켓"),
                    Cloth(R.drawable.sweater, "스웨터"),
                    Cloth(R.drawable.cardigan, "두꺼운 가디건"),
                    Cloth(R.drawable.cotton_pants, "면바지"),
                    Cloth(R.drawable.jeans, "청바지")

                )
            in 13..16 ->
                arrayListOf(
                    Cloth(R.drawable.jacket, "얇은 자켓"),
                    Cloth(R.drawable.sweater, "스웨터"),
                    Cloth(R.drawable.cardigan, "얇은 가디건"),
                    Cloth(R.drawable.hoodie, "후드티"),
                    Cloth(R.drawable.mantoman, "맨투맨"),
                    Cloth(R.drawable.cotton_pants, "면바지"),
                    Cloth(R.drawable.jeans, "청바지"),
                    Cloth(R.drawable.slacks, "슬랙스")
                )
            in 16..19 ->
                arrayListOf(
                    Cloth(R.drawable.long_sleeve, "긴팔티"),
                    Cloth(R.drawable.shirt, "와이셔츠"),
                    Cloth(R.drawable.hoodie, "후드티"),
                    Cloth(R.drawable.mantoman, "맨투맨"),
                    Cloth(R.drawable.cotton_pants, "면바지"),
                    Cloth(R.drawable.jeans, "청바지"),
                    Cloth(R.drawable.slacks, "슬랙스")
                )
            in 20..24 ->
                arrayListOf(
                    Cloth(R.drawable.tshirt, "반팔티"),
                    Cloth(R.drawable.long_sleeve, "얇은 긴팔티"),
                    Cloth(R.drawable.summer_shirts, "여름 셔츠"),
                    Cloth(R.drawable.linen, "여름바지"),
                    Cloth(R.drawable.onepiece, "원피스"),
                    Cloth(R.drawable.skirt, "치마"),
                    Cloth(R.drawable.slacks, "슬랙스"),
                    Cloth(R.drawable.shorts_2, "반바지")
                )
            else ->
                arrayListOf(
                    Cloth(R.drawable.shirt, "반팔티"),
                    Cloth(R.drawable.sleeveless, "민소매"),
                    Cloth(R.drawable.onepiece, "원피스"),
                    Cloth(R.drawable.short_onepiece, "짧은 원피스"),
                    Cloth(R.drawable.shorts, "짧은 반바지"),
                    Cloth(R.drawable.shorts_2, "반바지")
                )
        }
}
