package com.smrahmadi.cityguid.data.model.api


import com.google.gson.annotations.SerializedName


data class Response(

    @field:SerializedName("suggestedFilters")
    val suggestedFilters: SuggestedFilters,

    @field:SerializedName("totalResults")
    val totalResults: Int,

    @field:SerializedName("suggestedRadius")
    val suggestedRadius: Int,

    @field:SerializedName("headerFullLocation")
    val headerFullLocation: String,

    @field:SerializedName("warning")
    val warning: Warning,

    @field:SerializedName("headerLocationGranularity")
    val headerLocationGranularity: String,

    @field:SerializedName("groups")
    val groups: List<GroupsItem?>,

    @field:SerializedName("suggestedBounds")
    val suggestedBounds: SuggestedBounds,

    @field:SerializedName("headerLocation")
    val headerLocation: String
)