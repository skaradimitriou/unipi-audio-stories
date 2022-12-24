package com.stathis.unipiaudiostories.models.domain

sealed class Result {
    data class Loading(val isLoading: Boolean? = true) : Result()
    data class Success(val data: Any) : Result()
    data class Failure(val message: String?) : Result()
}