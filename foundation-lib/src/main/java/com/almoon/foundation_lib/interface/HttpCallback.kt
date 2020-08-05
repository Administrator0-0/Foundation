package com.almoon.foundation_lib.`interface`

import com.almoon.foundation_lib.common.HttpResult
import okhttp3.ResponseBody

interface HttpCallback<T> {
    fun onSuccess(result: HttpResult<T>?)

    fun onFail(result: ResponseBody)

    fun onFail()

    fun onFail(errorMessage: String?)
}