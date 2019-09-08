package com.smrahmadi.cityguid

import android.app.Application
import com.smrahmadi.cityguid.data.remote.ApiClient
import com.smrahmadi.cityguid.data.remote.ApiInterface

class App : Application() {

    companion object {
        lateinit var apiInterface: ApiInterface
    }

    override fun onCreate() {
        super.onCreate()
        apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
    }
}