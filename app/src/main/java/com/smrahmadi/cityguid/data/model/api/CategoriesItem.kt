package com.smrahmadi.cityguid.data.model.api


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoriesItem(

	@field:SerializedName("pluralName")
	val pluralName: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("icon")
	val icon: Icon? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("shortName")
	val shortName: String? = null,

	@field:SerializedName("primary")
	val primary: Boolean? = null
) : Parcelable