package com.almoon.foundation_lib.common

import com.almoon.foundation_lib.`interface`.HttpCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HttpTaskNode constructor(private val call: Call<Any>, val httpCallback: HttpCallback<Any>) {
    var child: ArrayList<HttpTaskNode> = ArrayList()

    fun execute() {

    }
}