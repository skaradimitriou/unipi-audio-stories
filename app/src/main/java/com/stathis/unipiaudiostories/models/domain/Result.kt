package com.stathis.unipiaudiostories.models.domain

/**
 * Sealed class to handle Success and Failure cases for api calls and callbacks.
 */

sealed class Result<T>(
    var data: T? = null,
    var error: String? = null
) {
    class Loading<T>() : Result<T>()
    class Success<T>(data: T) : Result<T>(data = data)
    class Failure<T>(error: String) : Result<T>(error = error)
}