package com.smrahmadi.cityguid.data.model.api


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Meta(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("requestId")
	val requestId: String? = null
) : Parcelable