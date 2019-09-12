package com.smrahmadi.cityguide.data.model.api


import com.google.gson.annotations.SerializedName


data class Icon(

    @field:SerializedName("prefix")
    val prefix: String,

    @field:SerializedName("suffix")
    val suffix: String
)