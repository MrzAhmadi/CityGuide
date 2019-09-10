package com.smrahmadi.cityguid.data.cast

import android.location.Location
import com.smrahmadi.cityguid.data.model.Item
import com.smrahmadi.cityguid.data.model.api.ItemsItem

class ItemDataCast {

    companion object {

        private const val ICON_SIZE = "32"

        fun cast(list: List<ItemsItem?>): ArrayList<Item?> {
            val items: ArrayList<Item?> = ArrayList()
            for (item in list) {
                items.add(
                    Item(
                        name = item!!.venue.name,
                        type = item.venue.categories[0]!!.name,
                        distance = generateDistance(item.venue.location.distance),
                        icon = generateIconUrl(item),
                        location = generateLocation(
                            item.venue.location.lat,
                            item.venue.location.lng
                        )
                    )
                )
            }
            return items
        }

        private fun generateIconUrl(item: ItemsItem): String {
            val url = StringBuilder()
                .append(item.venue.categories[0]!!.icon.prefix)
                .append(ICON_SIZE)
                .append(item.venue.categories[0]!!.icon.suffix)
            return url.toString()
        }

        private fun generateLocation(latitude: Double, longitude: Double): Location {
            val location = Location("")
            location.latitude = latitude
            location.longitude = longitude
            return location
        }

        private fun generateDistance(distance: Int): String {
            return if (distance < 1000)
                "$distance m"
            else
                "${distance / 1000} km"
        }
    }
}