package com.almoon.foundation

import retrofit2.Call
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST


interface RetrofitService {

    @POST("user/login")
    fun login(@Body jsonObject: RequestBody): Call<CommonReturn>

}