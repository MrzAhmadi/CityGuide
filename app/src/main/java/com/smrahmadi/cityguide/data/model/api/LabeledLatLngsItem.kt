package com.smrahmadi.cityguide.data.model.api


import com.google.gson.annotations.SerializedName


data class LabeledLatLngsItem(

    @field:SerializedName("lng")
    val lng: Double,

    @field:SerializedName("label")
    val label: String,

    @field:SerializedName("lat")
    val lat: Double
)