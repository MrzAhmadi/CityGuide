package com.smrahmadi.cityguid.data.model.api


import com.google.gson.annotations.SerializedName


data class LabeledLatLngsItem(

	@field:SerializedName("lng")
	val lng: Double? = null,

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
)