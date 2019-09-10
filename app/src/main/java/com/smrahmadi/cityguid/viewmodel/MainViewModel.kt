package com.smrahmadi.cityguid.viewmodel

import android.util.Log
import android.widget.AbsListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.smrahmadi.cityguid.data.cast.ItemDataCast
import com.smrahmadi.cityguid.data.repository.FoursquareRepository
import com.smrahmadi.cityguid.view.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainViewModel(
    private var view: MainActivity,
    private var foursquareRepository: FoursquareRepository
) : ViewModel() {

    companion object {
        const val REQUEST_SIZE = 50
    }

    private var isScrolling = false
    private var isLoading = true
    private var currentItems: Int = 0
    private var totalLoadedItems: Int = 0
    private var scrollOutItems: Int = 0
    private var positionInScroll: Int = 0
    private var lastPositionInView = -1
    private var totalItems = 0

    init {
        initInfiniteScroll()
    }

    fun setLoading(isLoading: Boolean) {
        this.isLoading = isLoading
    }

    fun getList(
        ll: String,
        v: String,
        limit: Int,
        offset: Int
    ) {
        foursquareRepository.locations(
            ll,
            v
            , limit,
            offset
        ).observe(view, Observer {

            if (it.hasException())
                it.throwable?.message?.let { it1 -> view.showError(it1) }
            else {
                if (it.data!!.meta.code == 200) {
                    val groups = it.data.response.groups[0]
                    view.loadList(ItemDataCast.cast(groups!!.items))
                    totalItems = it.data.response.totalResults
                    view.listItemDecoration.setItemCount(totalItems)
                } else
                    view.showError(it.data.meta.errorDetail)
            }
        })
    }

    private fun initInfiniteScroll() {
        view.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrolling = true
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItems = view.mLayoutManager.childCount
                totalLoadedItems = view.mLayoutManager.itemCount
                scrollOutItems = view.mLayoutManager.findFirstVisibleItemPosition()
                positionInScroll = currentItems + scrollOutItems

                if (!isLoading &&
                    isScrolling &&
                    positionInScroll == totalLoadedItems &&
                    totalLoadedItems < totalItems
                ) {
                    isScrolling = false
                    view.adapter.addLoading()
                    getList(
                        "40.74224,-73.99386",
                        "20190910",
                        REQUEST_SIZE,
                        totalLoadedItems
                    )
                }

                if (isScrolling && !isLoading && lastPositionInView != positionInScroll) {
                    lastPositionInView = positionInScroll
                    Log.e(
                        "isScrolling",
                        "lastPositionInView: $lastPositionInView ||| totalLoadedItems: $totalLoadedItems"
                    )
                }

            }
        })
    }
}