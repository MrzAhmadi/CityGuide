package com.smrahmadi.cityguid.data.model.api


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VenuePage(

	@field:SerializedName("id")
	val id: String? = null
) : Parcelable