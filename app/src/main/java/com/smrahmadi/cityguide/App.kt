package com.smrahmadi.cityguide

import android.app.Application
import android.content.Context
import com.smrahmadi.cityguide.data.remote.ApiClient
import com.smrahmadi.cityguide.data.remote.ApiInterface

class App : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: App? = null
        lateinit var apiInterface: ApiInterface
        fun getContext(): Context = instance!!.applicationContext

    }

    override fun onCreate() {
        super.onCreate()
        apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
    }
}