package com.smrahmadi.cityguide.data.cast

import android.location.Location
import com.smrahmadi.cityguide.data.model.Item
import com.smrahmadi.cityguide.data.model.api.ItemsItem

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
                            item.venue.location.lng,
                            item.venue.name
                        ),
                        address = generateAddress(item.venue.location.formattedAddress)
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

        private fun generateLocation(latitude: Double, longitude: Double, name: String): Location {
            val location = Location(name)
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

        private fun generateAddress(formattedAddress: List<String?>): String {
            var address: String? = null
            formattedAddress.asReversed().forEach {
                address = if (address == null)
                    it
                else
                    " $it"
            }
            return address!!
        }
    }
}