package com.smrahmadi.cityguid.data.model.api


import com.google.gson.annotations.SerializedName


data class Meta(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("errorType")
    val errorType: String? = null,

    @field:SerializedName("requestId")
    val requestId: String? = null,

    @field:SerializedName("errorDetail")
    val errorDetail: String? = null
)