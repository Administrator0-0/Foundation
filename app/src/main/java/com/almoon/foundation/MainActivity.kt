package com.almoon.foundation

//import com.almoon.foundation_lib.`interface`.HttpMapFun
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.almoon.foundation_annotation.ObserveFun
import com.almoon.foundation_lib.Foundation
import com.almoon.foundation_lib.common.HttpResult
import com.almoon.foundation_lib.interfaces.HttpMapFun
import retrofit2.Call

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
        Foundation.getHttp().nestedRequest(call)
            .flatMap<CommonReturn>(object: HttpMapFun<CommonReturn, CommonReturn> {
                override fun map(result: HttpResult<CommonReturn>?): Call<CommonReturn> {
                    Log.d("aaa", "1"+ result!!.getData()!!.getMessage())
                    val loginSend2 = LoginSend()
                    loginSend2.setEmail("233@233.com")
                    loginSend2.setPassword("233233")
                    val requestBody2 = Foundation.getHttp().getJsonRequestBody(loginSend2)
                    val service2 = Foundation.getHttp().getGsonService("http://47.93.139.51:8000/", RetrofitService::class)
                    return service2.login(requestBody2)
                }
            })
            .flatMap<CommonReturn>(object: HttpMapFun<CommonReturn, CommonReturn> {
                override fun map(result: HttpResult<CommonReturn>?): Call<CommonReturn> {
                    Log.d("aaa", "1"+ result!!.getData()!!.getMessage())
                    val loginSend2 = LoginSend()
                    loginSend2.setEmail("233@233.com")
                    loginSend2.setPassword("233233")
                    val requestBody2 = Foundation.getHttp().getJsonRequestBody(loginSend2)
                    val service2 = Foundation.getHttp().getGsonService("http://47.93.139.52:8000/", RetrofitService::class)
                    return service2.login(requestBody2)
                }
            }).execute()

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