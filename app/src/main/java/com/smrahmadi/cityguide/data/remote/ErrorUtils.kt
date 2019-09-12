package com.smrahmadi.cityguide.data.remote

import com.smrahmadi.cityguide.data.model.api.Data
import retrofit2.Response
import java.io.IOException

class ErrorUtils {

    companion object {
        fun parseError(response: Response<*>): Data? {
            val converter = ApiClient.retrofit!!
                .responseBodyConverter<Data>(Data::class.java, arrayOfNulls(0))
            val error: Data?
            try {
                error = converter.convert(response.errorBody()!!)
            } catch (e: IOException) {
                return null
            }
            return error
        }
    }
}