package com.smrahmadi.cityguide.data.model.api


import com.google.gson.annotations.SerializedName


data class Warning(

	@field:SerializedName("text")
    val text: String
)