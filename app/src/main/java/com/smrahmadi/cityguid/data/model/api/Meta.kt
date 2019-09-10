package com.smrahmadi.cityguid.data.model.api


import com.google.gson.annotations.SerializedName


data class Meta(

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("errorType")
    val errorType: String,

    @field:SerializedName("requestId")
    val requestId: String,

    @field:SerializedName("errorDetail")
    val errorDetail: String
)