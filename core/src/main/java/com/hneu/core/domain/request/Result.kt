package com.hneu.core.domain.request

sealed class Result<T> {
    class Loading(): Result<Nothing>()
    class Success<T>(val data: T) : Result<T>()
    class Completed(): Result<Nothing>()
    class Error(throwable: Throwable) : Result<Nothing>()
}