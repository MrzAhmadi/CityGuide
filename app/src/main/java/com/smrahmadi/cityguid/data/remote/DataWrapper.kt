package com.smrahmadi.cityguid.data.remote

class DataWrapper<T>(val throwable: Throwable?, val data: T?) {
    fun hasException(): Boolean {
        return throwable != null
    }
}