package com.smrahmadi.cityguid.data.model.api


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Venue(

	@field:SerializedName("venuePage")
	val venuePage: VenuePage? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: Location? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("categories")
	val categories: List<CategoriesItem?>? = null
) : Parcelable