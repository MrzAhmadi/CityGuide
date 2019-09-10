package com.smrahmadi.cityguid.data.model.api


import com.google.gson.annotations.SerializedName


data class SuggestedFilters(

    @field:SerializedName("header")
    val header: String,

    @field:SerializedName("filters")
    val filters: List<FiltersItem?>
)