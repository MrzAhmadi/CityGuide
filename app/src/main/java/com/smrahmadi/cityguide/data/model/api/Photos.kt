package com.smrahmadi.cityguide.data.model.api


import com.google.gson.annotations.SerializedName


data class Photos(

    @field:SerializedName("count")
    val count: Int,

    @field:SerializedName("groups")
    val groups: List<Any?>
)