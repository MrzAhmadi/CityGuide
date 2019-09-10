package com.smrahmadi.cityguid.data.model

import android.location.Location
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item
    (
    var name: String,
    var type: String,
    var icon: String,
    var location: Location,
    var distance: String
) : Parcelable