package com.smrahmadi.cityguide.data.model.api


import com.google.gson.annotations.SerializedName


data class Ne(

    @field:SerializedName("lng")
    val lng: Double,

    @field:SerializedName("lat")
    val lat: Double
)