package com.almoon.foundation_lib.component

import com.almoon.foundation_lib.`interface`.HttpCallback
import com.almoon.foundation_lib.common.HttpResult
import com.google.gson.Gson
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class HttpComponent {
    fun <T : Any> getGsonService(baseUrl: String, service: T): T {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(service.javaClass)
    }

    fun <T : Any> getScalarsService(baseUrl: String, service: T): T {
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(service.javaClass)
    }

    fun <T> getJsonRequestBody(bean: T): RequestBody? {
        val gson = Gson()
        val json = gson.toJson(bean)
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)
    }

    fun <T> requestHttp(call: Call<T>, httpCallback: HttpCallback<T>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(
                call: Call<T>,
                response: Response<T>
            ) {
                val httpReturn: T? = response.body()
                if (httpReturn != null) {
                    httpCallback.onSuccess(HttpResult(httpReturn))
                } else {
                    httpCallback.onFail()
                }
            }

            override fun onFailure(
                call: Call<T>,
                t: Throwable
            ) {
                httpCallback.onFail(t.message)
            }
        })
    }

}