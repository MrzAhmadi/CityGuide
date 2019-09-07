package com.smrahmadi.cityguid.data.model.api


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ItemsItem(

	@field:SerializedName("venue")
	val venue: Venue? = null,

	@field:SerializedName("reasons")
	val reasons: Reasons? = null,

	@field:SerializedName("summary")
	val summary: String? = null,

	@field:SerializedName("reasonName")
	val reasonName: String? = null,

	@field:SerializedName("type")
	val type: String? = null
) : Parcelable