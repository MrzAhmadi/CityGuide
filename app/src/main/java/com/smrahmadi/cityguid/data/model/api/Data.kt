package com.smrahmadi.cityguid.data.model.api

import com.google.gson.annotations.SerializedName

data class Data(

    @field:SerializedName("meta")
    val meta: Meta? = null,

    @field:SerializedName("response")
    val response: Response? = null
)