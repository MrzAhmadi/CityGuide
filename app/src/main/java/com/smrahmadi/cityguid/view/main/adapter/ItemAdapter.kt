package com.smrahmadi.cityguid.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smrahmadi.cityguid.R
import com.smrahmadi.cityguid.data.model.Item
import com.smrahmadi.cityguid.view.main.viewholder.ItemViewHolder
import com.smrahmadi.cityguid.view.main.viewholder.LoadingViewHolder

class ItemAdapter(private var callback: Listener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_LOCATION = 0
        private const val ITEM_LOADING = 1
    }

    interface Listener {
        fun onItemClick(item: Item)
    }

    private var items = arrayListOf<Item?>()

    override fun getItemViewType(position: Int): Int {
        return if (items[position] != null)
            ITEM_LOCATION
        else
            ITEM_LOADING
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_LOCATION ->
                ItemViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_location,
                        parent,
                        false
                    ), callback
                )
            else ->
                LoadingViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_loading,
                        parent,
                        false
                    )
                )

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (items[position] != null)
            (holder as ItemViewHolder).bind(items[position])
    }

    fun addItems(items: ArrayList<Item?>) {
        if (this.items.size > 0) {
            val lastPosition = items.size
            this.items.addAll(items)
            notifyItemChanged(lastPosition, items.size)
        } else {
            this.items.addAll(items)
            notifyDataSetChanged()
        }
    }

    fun addLoading() {
        if (items.isEmpty() || items[items.size - 1] != null) {
            items.add(null)
            notifyItemInserted(items.size - 1)
        }
    }

    fun removeLoading() {
        if (items.isNotEmpty() && items[items.size - 1] == null) {
            val loadingPosition = items.size - 1
            items.remove(null)
            notifyItemRemoved(loadingPosition)
        }
    }

    fun clearList() {
        items.clear()
        notifyDataSetChanged()
    }
}