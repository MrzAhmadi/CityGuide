package com.smrahmadi.cityguide.data.model.api


import com.google.gson.annotations.SerializedName


data class GroupsItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("items")
    val items: List<ItemsItem?>
)