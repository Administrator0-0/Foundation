package com.almoon.foundation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.almoon.foundation_annotation.ObserveFun
import com.almoon.foundation_lib.Foundation
import com.almoon.foundation_lib.`interface`.HttpCallback
import com.almoon.foundation_lib.common.HttpResult
import okhttp3.ResponseBody

class MainActivity : AppCompatActivity() {

    lateinit var viewModel : TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(TestViewModel::class.java)
        Foundation.getVM().bind(this)
        viewModel.getTest().value = 1


        val loginSend = LoginSend()
        loginSend.setEmail("233@233.com")
        loginSend.setPassword("233233")
        val requestBody = Foundation.getHttp().getJsonRequestBody(loginSend)
        val service = Foundation.getHttp().getGsonService("http://47.93.139.52:8000/", RetrofitService::class)
        val call = service.login(requestBody)
        Foundation.getHttp().requestHttp(call, object : HttpCallback<CommonReturn> {
            override fun onSuccess(result: HttpResult<CommonReturn>?) {
                if (result != null) {
                    Log.d("aaa", "login: " + result.getData()!!.getMessage())
                }
            }

            override fun onFail() {
                Log.d("aaa","fail")
            }

            override fun onFail(errorMessage: String?) {
                if (errorMessage != null) {
                    Log.d("aaa", errorMessage)
                }
            }

            override fun onFail(result: ResponseBody) {

            }
        })


    }


    @ObserveFun("viewModel.getTest()")
    fun test() {
        Log.d("aaa","aaa")
    }
    //@ObserveFun("viewModel.getTest()")
    fun test2() {
        Log.d("aaa","bbb")
    }

}