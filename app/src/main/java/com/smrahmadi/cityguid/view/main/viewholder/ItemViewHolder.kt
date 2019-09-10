package com.smrahmadi.cityguid.view.main.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.smrahmadi.cityguid.R
import com.smrahmadi.cityguid.data.model.Item
import com.smrahmadi.cityguid.helper.GlideApp
import com.smrahmadi.cityguid.view.main.adapter.ItemAdapter
import kotlinx.android.synthetic.main.item_location.view.*

class ItemViewHolder(
    itemView: View,
    private var callback: ItemAdapter.Listener
) : RecyclerView.ViewHolder(itemView) {

    companion object {
        private const val TAG = "ItemViewHolder"
    }

    fun bind(item: Item?) {
        itemView.name.text = item!!.name
        itemView.type.text = item.type
        itemView.distance.text = item.distance

        itemView.setOnClickListener {
            callback.onItemClick(item)
        }

        val iconSize = itemView.resources.getDimension(R.dimen.dp_32).toInt()

        GlideApp.with(itemView.context)
            .load(item.icon)
            .transition(withCrossFade())
            .override(iconSize, iconSize)
            .into(itemView.typeIcon)
    }
}