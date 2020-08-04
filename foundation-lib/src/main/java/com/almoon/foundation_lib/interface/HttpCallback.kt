package com.almoon.foundation_lib.`interface`

import com.almoon.foundation_lib.common.HttpResult

interface HttpCallback<T> {
    fun onSuccess(result: HttpResult<T>?)

    fun onFail()

    fun onFail(errorMessage: String?)
}