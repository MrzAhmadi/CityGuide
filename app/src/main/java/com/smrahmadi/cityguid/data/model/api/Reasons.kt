package com.smrahmadi.cityguid.data.model.api


import com.google.gson.annotations.SerializedName


data class Reasons(

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("items")
	val items: List<ItemsItem?>? = null
)