package com.example.restaurantws.core

sealed class Resource<T> (val data: T? = null, val error: Throwable? =null){
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(exception: Throwable) : Resource<T>(error = exception)
    class Loading<T>() : Resource<T>()
    class Empty<T>() : Resource<T>()
}