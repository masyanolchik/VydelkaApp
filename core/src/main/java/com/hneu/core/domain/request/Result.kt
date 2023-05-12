package com.hneu.core.domain.request

sealed class Result {
    class Loading(): Result()
    class Success<T>(val data: T) : Result()
    class Error(throwable: Throwable) : Result()
}