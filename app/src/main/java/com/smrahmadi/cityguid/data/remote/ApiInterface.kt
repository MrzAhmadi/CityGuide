package com.smrahmadi.cityguid.data.remote

import com.smrahmadi.cityguid.data.model.api.Data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
    @GET("/v2/venues/explore")
    fun locations(
        @Query("ll") ll: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("v") v: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("sortByDistance") sortByDistance: Boolean
    ): Call<Data>
}