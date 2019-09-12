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
import com.smrahmadi.cityguid.R
import com.smrahmadi.cityguid.data.cast.ItemDataCast
import com.smrahmadi.cityguid.data.remote.ApiClient
import com.smrahmadi.cityguid.data.repository.FoursquareRepository
import com.smrahmadi.cityguid.data.repository.LocationRepository
import com.smrahmadi.cityguid.utils.NetworkUtils
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
        const val MINIMUM_DISTANCE = 100
    }

    private var isFirstLoad = true
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
        if (isLocationServiceEnabled()) {
            val provider = LocationManager.NETWORK_PROVIDER
            locationRepository.locationManager.requestLocationUpdates(
                provider,
                400,
                1f,
                locationRepository
            )
            locationRepository.getLocationData()!!.observe(activity, Observer {
                location = it
                when {
                    NetworkUtils.isNetworkAvailable(activity) &&
                            locationRepository.getLocation() == null -> {
                        locationRequest = "${location!!.latitude},${location!!.longitude}"
                        getListFrom(0)
                    }
                    !NetworkUtils.isNetworkAvailable(activity) &&
                            locationRepository.getLocation() != null &&
                            isFirstLoad -> {
                        locationRequest = locationRepository.getLocation()!!
                        activity.showError(
                            activity.getString(R.string.load_data_from_cache),
                            false
                        )
                        getListFrom(0)
                    }
                    !NetworkUtils.isNetworkAvailable(activity) &&
                            locationRepository.getLocation() == null &&
                            isFirstLoad -> {
                        locationRequest = "${location!!.latitude},${location!!.longitude}"
                        activity.showError(
                            activity.getString(R.string.no_internet_no_cache),
                            true
                        )

                    }
                    else -> {
                        if (oldLocationGenerator().distanceTo(location) < MINIMUM_DISTANCE) {
                            locationRequest = locationRepository.getLocation()!!
                            //location not changed load from cache
                            if (isFirstLoad)
                                getListFrom(0)
                        } else {
                            //location changed , loading fresh data
                            locationRequest = "${location!!.latitude},${location!!.longitude}"
                            getListFrom(0)
                        }
                    }
                }

                activity.showList()
            })
        } else {
            activity.showError(activity.getString(R.string.enable_gps), false)
            activity.openGpsSettings()
            activity.finish()
        }
    }

    private fun isLocationServiceEnabled(): Boolean {
        return locationRepository.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun getList(
        offset: Int
    ) {
        foursquareRepository.locations(
            locationRequest,
            REQUEST_SIZE,
            offset
        ).observe(activity, Observer {
            if (it.hasException())
                it.throwable?.message?.let { it1 -> activity.showError(it1, true) }
            else {
                if (it.data != null) {
                    if (it.data.meta.code == 200) {
                        isLoading = false
                        val groups = it.data.response.groups[0]
                        activity.loadList(ItemDataCast.cast(groups!!.items))
                        totalItems = it.data.response.totalResults
                        activity.listItemDecoration.setItemCount(totalItems)
                        isFirstLoad = false
                        locationRepository.saveLocation(locationRequest)
                    } else
                        activity.showError(it.data.meta.errorDetail, true)
                } else
                    activity.showError(activity.getString(R.string.no_data_in_cache), true)
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
        if (NetworkUtils.isNetworkAvailable(activity) && locationRepository.getLocation() != null) {
            if (oldLocationGenerator().distanceTo(location) > MINIMUM_DISTANCE)
                locationRequest = "${location!!.latitude},${location!!.longitude}"
            else {
                locationRequest = locationRepository.getLocation()!!
                ApiClient.setForceCache(true)
            }
        }
        isLoading = true
        getList(
            offset
        )
    }

    private fun oldLocationGenerator(): Location {
        val oldLocation = Location("oldLocation")
        val locArray = locationRepository.getLocation()!!.split(",")
        oldLocation.latitude = locArray[0].toDouble()
        oldLocation.longitude = locArray[1].toDouble()
        return oldLocation
    }

    fun locationListenerTurnOff() {
        locationRepository.locationManager.removeUpdates(locationRepository)
    }
}