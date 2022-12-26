package com.stathis.unipiaudiostories.models.domain

/**
 * Sealed class to handle Success and Failure cases for api calls and callbacks.
 */

sealed class Result {
    data class Success(val data: Any) : Result()
    data class Failure(val message: String) : Result()
}