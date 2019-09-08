package com.smrahmadi.cityguid.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.smrahmadi.cityguid.App
import com.smrahmadi.cityguid.data.model.api.Data
import com.smrahmadi.cityguid.data.remote.DataWrapper
import com.smrahmadi.cityguid.data.remote.ErrorUtils
import retrofit2.Call
import retrofit2.Callback


class FoursquareRepository {


    companion object {
        const val TAG = "FoursquareRepository"
        //They should be encrypted for real state
        const val clientId = "TI4OXJ1HKNAXS1DCBTMNC5VE1W2U5BAHH23SCPUCDPP01UXQ"
        const val clientSecret = "JRW525SO30OJM4AY5DRFL1VEKM5R2UB03DZT3D1QAMCJJUBW"
    }

    fun locations(
        ll: String,
        v: String,
        limit: Int,
        offset: Int
    ): LiveData<DataWrapper<Data>> {
        val liveData: MutableLiveData<DataWrapper<Data>> = MutableLiveData()
        App.apiInterface.locations(
            ll,
            clientId,
            clientSecret,
            v,
            limit,
            offset
        ).enqueue(object : Callback<String> {

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.v(
                    TAG,
                    "onFailure: ${t.message}"
                )
                liveData.postValue(
                    DataWrapper(
                        t,
                        null
                    )
                )
            }

            override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
                if (response.isSuccessful) {
                    Log.v(
                        TAG,
                        "isSuccessful: ${response.body().toString()}"
                    )
                    liveData.postValue(
                        DataWrapper(
                            null,
                            Gson().fromJson(
                                response.body(),
                                Data::class.java
                            )
                        )
                    )
                } else {
                    val data = ErrorUtils.parseError(response)
                    Log.v(
                        TAG,
                        "!isSuccessful: ${data?.meta?.errorDetail}"
                    )
                    liveData.postValue(
                        DataWrapper(
                            null,
                            data
                        )
                    )
                }
            }
        })
        return liveData
    }

}