package com.smrahmadi.cityguid.data.model.api


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SuggestedBounds(

	@field:SerializedName("sw")
	val sw: Sw? = null,

	@field:SerializedName("ne")
	val ne: Ne? = null
) : Parcelable