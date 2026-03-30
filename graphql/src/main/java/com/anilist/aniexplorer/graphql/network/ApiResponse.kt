package com.anilist.aniexplorer.graphql.network

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val errorMessage: String, val throwable: Throwable? = null) : ApiResponse<Nothing>()
}
