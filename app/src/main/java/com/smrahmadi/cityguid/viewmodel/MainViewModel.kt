package com.smrahmadi.cityguid.viewmodel

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import android.widget.AbsListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.smrahmadi.cityguid.data.cast.ItemDataCast
import com.smrahmadi.cityguid.data.repository.FoursquareRepository
import com.smrahmadi.cityguid.data.repository.LocationRepository
import com.smrahmadi.cityguid.view.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainViewModel(
    private var activity: MainActivity,
    private var foursquareRepository: FoursquareRepository,
    private var locationRepository: LocationRepository
) : ViewModel() {

    companion object {
        const val REQUEST_SIZE = 50
        const val PERMISSIONS_REQUEST_LOCATION = 99

    }

    private var isScrolling = false
    private var isLoading = true
    private var currentItems: Int = 0
    private var totalLoadedItems: Int = 0
    private var scrollOutItems: Int = 0
    private var positionInScroll: Int = 0
    private var lastPositionInView = -1
    private var totalItems = 0

    private var location: Location? = null
    private lateinit var locationRequest: String

    init {
        initInfiniteScroll()
    }


    fun initLocationPermissions() {
        val permission = android.Manifest.permission.ACCESS_FINE_LOCATION
        val res = activity.checkCallingOrSelfPermission(permission)
        if (res == PackageManager.PERMISSION_GRANTED)
            locationUpdate()
        else
            activity.showRequestPermissionsDialog()
    }

    @SuppressLint("MissingPermission")
    fun locationUpdate() {
        val provider = LocationManager.NETWORK_PROVIDER
        locationRepository.locationManager.requestLocationUpdates(
            provider,
            400,
            1f,
            locationRepository
        )
        locationRepository.getLocationData()!!.observe(activity, Observer {
            location = it
            locationRequest = "${location!!.latitude},${location!!.longitude}"
            getListFrom(0)
            activity.showList()
        })
    }

    fun getList(
        v: String,
        limit: Int,
        offset: Int
    ) {
        foursquareRepository.locations(
            locationRequest,
            v
            , limit,
            offset
        ).observe(activity, Observer {

            if (it.hasException())
                it.throwable?.message?.let { it1 -> activity.showError(it1) }
            else {
                if (it.data!!.meta.code == 200) {
                    isLoading = false
                    val groups = it.data.response.groups[0]
                    activity.loadList(ItemDataCast.cast(groups!!.items))
                    totalItems = it.data.response.totalResults
                    activity.listItemDecoration.setItemCount(totalItems)
                } else
                    activity.showError(it.data.meta.errorDetail)
            }
        })
    }

    private fun initInfiniteScroll() {
        activity.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrolling = true
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItems = activity.mLayoutManager.childCount
                totalLoadedItems = activity.mLayoutManager.itemCount
                scrollOutItems = activity.mLayoutManager.findFirstVisibleItemPosition()
                positionInScroll = currentItems + scrollOutItems

                if (!isLoading &&
                    isScrolling &&
                    positionInScroll == totalLoadedItems &&
                    totalLoadedItems < totalItems
                ) {
                    isScrolling = false
                    activity.adapter.addLoading()
                    getListFrom(totalLoadedItems)
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

    fun getListFrom(offset: Int) {
        isLoading = true
        getList(
            "20190910",
            REQUEST_SIZE,
            offset
        )
    }


}