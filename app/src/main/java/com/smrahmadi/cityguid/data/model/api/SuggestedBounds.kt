package com.smrahmadi.cityguid.data.model.api


import com.google.gson.annotations.SerializedName


data class SuggestedBounds(

	@field:SerializedName("sw")
	val sw: Sw? = null,

	@field:SerializedName("ne")
	val ne: Ne? = null
)