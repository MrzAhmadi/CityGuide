package com.smrahmadi.cityguid.data.model.api


import com.google.gson.annotations.SerializedName


data class Sw(

    @field:SerializedName("lng")
    val lng: Double,

    @field:SerializedName("lat")
    val lat: Double
)