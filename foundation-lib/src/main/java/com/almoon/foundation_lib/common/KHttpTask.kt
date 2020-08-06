package com.almoon.foundation_lib.common

import android.util.Log
import com.almoon.foundation_lib.interfaces.HttpMapFun
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KHttpTask<T> {
    var parent: KHttpTask<*>? = null
    var child: KHttpTask<*>? = null
    var mapFun: HttpMapFun<*, *>? = null
    var call: Call<T>? = null
    private val TAG = "HttpTask"

    constructor(parent: KHttpTask<*>?) {
        this.parent = parent
    }

    constructor(call: Call<T>?) {
        this.call = call
    }

    fun <V> flatMap(map: HttpMapFun<T, V>?): KHttpTask<*>? {
        mapFun = map
        child = KHttpTask<V>(this)
        return child
    }

    fun execute() {
        var root: KHttpTask<*>? = this@KHttpTask.parent
        while (true) {
            root = if (root!!.parent != null) {
                root.parent
            } else {
                break
            }
        }
        val position = 0
        root!!.realExecute(position)
    }

    fun realExecute(position: Int) {
        call!!.enqueue(object : Callback<T?> {
            override fun onResponse(
                call: Call<T?>,
                response: Response<T?>
            ) {
                if (response.body() != null) {
                    if (mapFun != null) {
//                        val child = mapFun!!.map(HttpResult<Any?>(response.body()))
//                        this@KHttpTask.child.call = child
//                        this@KHttpTask.child!!.realExecute(position)
                    }
                }
            }

            override fun onFailure(call: Call<T?>, t: Throwable) {
                Log.e(TAG, "Task" + position + " onFailure: " + t.message)
            }
        })
    }
}