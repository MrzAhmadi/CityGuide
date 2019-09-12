package com.smrahmadi.cityguid.data.remote

import com.smrahmadi.cityguid.App
import com.smrahmadi.cityguid.utils.NetworkUtils.isNetworkAvailable
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        var retrofit: Retrofit? = null
        private const val BASE_URL = "https://api.foursquare.com"

        private const val cacheSize = (5 * 1024 * 1024).toLong()
        private val myCache = Cache(App.getContext().cacheDir, cacheSize)
        private var isForceCache = false

        private val okHttpClient = OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (isNetworkAvailable(App.getContext()) && !isForceCache)
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, max-age=" + 5
                    ).build()
                else {
                    isForceCache = false
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).build()
                }
                chain.proceed(request)
            }
            .build()

        fun getClient(): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            }
            return retrofit!!
        }

        fun setForceCache(isForce: Boolean) {
            isForceCache = isForce
        }

    }
}