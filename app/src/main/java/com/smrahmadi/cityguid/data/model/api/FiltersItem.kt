package com.smrahmadi.cityguid.data.model.api


import com.google.gson.annotations.SerializedName


data class FiltersItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("key")
    val key: String
)