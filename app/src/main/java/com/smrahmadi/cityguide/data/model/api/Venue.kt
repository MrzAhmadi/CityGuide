package com.smrahmadi.cityguide.data.model.api


import com.google.gson.annotations.SerializedName


data class Venue(

    @field:SerializedName("venuePage")
    val venuePage: VenuePage,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("location")
    val location: Location,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("categories")
    val categories: List<CategoriesItem?>,

    @field:SerializedName("photos")
    val photos: Photos
)