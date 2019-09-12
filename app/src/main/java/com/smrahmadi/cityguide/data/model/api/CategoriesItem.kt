package com.smrahmadi.cityguide.data.model.api


import com.google.gson.annotations.SerializedName


data class CategoriesItem(

    @field:SerializedName("pluralName")
    val pluralName: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("icon")
    val icon: Icon,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("shortName")
    val shortName: String,

    @field:SerializedName("primary")
    val primary: Boolean
)