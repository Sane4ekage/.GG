package com.sane4ek.zefirgg.core.data

import android.util.Log
import retrofit2.Call
import retrofit2.Response

class CloudDataSource<T : Any> {

    suspend fun execute(call: suspend () -> Response<T>): ApiResult<T> {
        return try {
            val response = call.invoke()
            if (response.code() == 200 || response.code() == 201) {
                Log.i("cds_log", "Успешно = ${response.body()!!}")
                ApiResult.Success(data = response.body()!!)
            } else {
                Log.i("cds_log", "Неудача, код = ${response.code()}")
                ApiResult.Failure(statusCode = response.code())
            }
        } catch (e: Exception) {
            Log.i("cds_log", "Ошибка = $e")
            ApiResult.Failure()
        }
    }

}