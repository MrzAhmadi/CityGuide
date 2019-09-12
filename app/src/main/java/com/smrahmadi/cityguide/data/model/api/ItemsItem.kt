package com.smrahmadi.cityguide.data.model.api


import com.google.gson.annotations.SerializedName


data class ItemsItem(

    @field:SerializedName("venue")
    val venue: Venue,

    @field:SerializedName("reasons")
    val reasons: Reasons,

    @field:SerializedName("referralId")
    val referralId: String,

    @field:SerializedName("summary")
    val summary: String,

    @field:SerializedName("reasonName")
    val reasonName: String,

    @field:SerializedName("type")
    val type: String
)