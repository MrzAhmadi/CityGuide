package com.smrahmadi.cityguid.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        const val clientId = "K4CZBCZWZMC3CIYA3QEPOOVPIMCU2HIN4DPEN5E4CSFQWCCZ"
        const val clientSecret = "TOCUUOZFF0A4LEBJDFKL5CMZJ4DIDL4HCZJ3I1TE40023Y3C"
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
            offset,
            true
        ).enqueue(object : Callback<Data> {

            override fun onFailure(call: Call<Data>, t: Throwable) {
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

            override fun onResponse(call: Call<Data>, response: retrofit2.Response<Data>) {
                if (response.isSuccessful) {
                    Log.v(
                        TAG,
                        "isSuccessful: ${response.body().toString()}"
                    )
                    liveData.postValue(
                        DataWrapper(
                            null,
                            response.body()
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