package com.smrahmadi.cityguide.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.smrahmadi.cityguide.App
import com.smrahmadi.cityguide.data.model.api.Data
import com.smrahmadi.cityguide.data.remote.DataWrapper
import com.smrahmadi.cityguide.data.remote.ErrorUtils
import retrofit2.Call
import retrofit2.Callback


class FoursquareRepository {


    companion object {
        private const val TAG = "FoursquareRepository"
        //They should be encrypted for real state
        private const val clientId = "GPJINAM3K0Q2PAM1Q4TFAORXPQVUIVLZH2KQ3BA50YATOESY"
        private const val clientSecret = "JAABKKVTYYMSS2OLKTVYTVFVG4DUY4ZCFRKU1OATPOMRCPHP"
        private const val v = "20190910"
    }

    fun locations(
        ll: String,
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