package com.sane4ek.zefirgg.core.data

sealed class ApiResult <T: Any> {
    class Success<T : Any>( val data: T): ApiResult<T>()
    class Failure<T : Any>(val statusCode: Int = 0): ApiResult<T>()
}
