package com.smrahmadi.cityguid.data.model.api


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Reasons(

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("items")
	val items: List<ItemsItem?>? = null
) : Parcelable