package com.example.beercatalogue.domain.model

import com.google.gson.annotations.SerializedName

data class BeerModel(
    val id: Int,
    val name: String,
    val description: String,
    val tagline: String,
    @SerializedName("image_url") val imageURL: String,
    val abv: Float,
)
