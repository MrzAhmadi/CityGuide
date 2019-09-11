package com.smrahmadi.cityguid.view.main

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.StrictMode
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.smrahmadi.cityguid.R
import com.smrahmadi.cityguid.data.local.SharedPreferencesConnector
import com.smrahmadi.cityguid.data.model.Item
import com.smrahmadi.cityguid.data.repository.FoursquareRepository
import com.smrahmadi.cityguid.data.repository.LocationRepository
import com.smrahmadi.cityguid.utils.ListItemDecoration
import com.smrahmadi.cityguid.utils.longToast
import com.smrahmadi.cityguid.view.main.adapter.ItemAdapter
import com.smrahmadi.cityguid.viewmodel.MainViewModel
import com.smrahmadi.cityguid.viewmodel.MainViewModel.Companion.PERMISSIONS_REQUEST_LOCATION
import com.smrahmadi.cityguid.viewmodel.provider.FoursquareViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),
    SwipeRefreshLayout.OnRefreshListener,
    ItemAdapter.Listener {

    private val TAG = "MainActivity"

    private lateinit var viewModel: MainViewModel
    lateinit var adapter: ItemAdapter
    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var listItemDecoration: ListItemDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        initView()
        //Unused 'Hard Dependency' with dagger in real project
        viewModel = ViewModelProviders
            .of(
                this,
                FoursquareViewModelProvider(
                    this,
                    FoursquareRepository(),
                    LocationRepository(this, SharedPreferencesConnector(this))
                )
            )
            .get(MainViewModel::class.java)

        viewModel.initLocationPermissions()
        initList()
    }

    private fun initView() {
        setContentView(R.layout.activity_main)
        refreshLayout.setOnRefreshListener(this)
    }

    private fun initList() {
        listItemDecoration =
            ListItemDecoration(resources.getDimension(com.smrahmadi.cityguid.R.dimen.dp_8).toInt())
        mLayoutManager = LinearLayoutManager(this)
        list.layoutManager = mLayoutManager
        adapter = ItemAdapter(this)
        list.adapter = adapter
        list.addItemDecoration(listItemDecoration)
        adapter.addLoading()
    }

    fun showRequestPermissionsDialog() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.require_permissions))
            .setPositiveButton(
                R.string.ok
            ) { _, i ->
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_LOCATION
                )
            }
            .create()
            .show()
    }

    fun showList() {
        loading.visibility = GONE
        loadingText.visibility = GONE
        list.visibility = VISIBLE
    }

    fun loadList(items: ArrayList<Item?>) {
        adapter.removeLoading()
        refreshLayout.isRefreshing = false
        adapter.addItems(items)
    }

    fun showError(errorMessage: String, stopLoading: Boolean) {
        longToast(errorMessage)
        if (stopLoading) {
            adapter.removeLoading()
            refreshLayout.isRefreshing = false
        }
    }

    fun openGpsSettings() {
        val gpsOptionsIntent = Intent(
            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
        )
        startActivity(gpsOptionsIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    )
                        viewModel.locationUpdate()
                } else {
                    longToast(getString(R.string.require_permissions))
                    finish()
                }
                return
            }
        }
    }

    override fun onItemClick(item: Item) {
    }

    override fun onRefresh() {
        adapter.clearList()
        viewModel.getListFrom(0)
    }
}