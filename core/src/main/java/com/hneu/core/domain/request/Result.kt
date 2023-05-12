package com.hneu.core.domain.request

sealed class Result {
    class Loading(): Result()
    class Success<T>(val data: T) : Result()
    class Completed(): Result()
    class Error(throwable: Throwable) : Result()
}