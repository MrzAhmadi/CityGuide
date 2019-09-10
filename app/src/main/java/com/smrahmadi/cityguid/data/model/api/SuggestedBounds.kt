package com.smrahmadi.cityguid.data.model.api


import com.google.gson.annotations.SerializedName


data class SuggestedBounds(

    @field:SerializedName("sw")
    val sw: Sw,

    @field:SerializedName("ne")
    val ne: Ne
)