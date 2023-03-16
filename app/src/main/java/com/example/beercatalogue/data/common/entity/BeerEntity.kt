package com.example.beercatalogue.data.common.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "BeerCatalogue")
data class BeerEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val description: String,
    val tagline: String,
    @SerializedName("image_url") val imageURL: String,
    val abv: Float,
    @SerializedName("brewers_tips")
    val brewerTips: String
) : Parcelable
