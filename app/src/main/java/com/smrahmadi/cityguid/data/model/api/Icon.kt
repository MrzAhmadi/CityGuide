package com.smrahmadi.cityguid.data.model.api


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Icon(

	@field:SerializedName("prefix")
	val prefix: String? = null,

	@field:SerializedName("suffix")
	val suffix: String? = null
) : Parcelable