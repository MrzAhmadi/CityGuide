package com.smrahmadi.cityguid.data.model.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Response(

	@field:SerializedName("meta")
	val meta: Meta? = null,

	@field:SerializedName("response")
	val response: Response? = null,

	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	@field:SerializedName("suggestedRadius")
	val suggestedRadius: Int? = null,

	@field:SerializedName("headerFullLocation")
	val headerFullLocation: String? = null,

	@field:SerializedName("warning")
	val warning: Warning? = null,

	@field:SerializedName("headerLocationGranularity")
	val headerLocationGranularity: String? = null,

	@field:SerializedName("groups")
	val groups: List<GroupsItem?>? = null,

	@field:SerializedName("suggestedBounds")
	val suggestedBounds: SuggestedBounds? = null,

	@field:SerializedName("headerLocation")
	val headerLocation: String? = null
) : Parcelable