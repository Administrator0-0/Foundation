package com.almoon.foundation

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.almoon.foundation_annotation.ObserveFun
import com.almoon.foundation_lib.Foundation
import com.almoon.foundation_lib.common.EventMsg
import com.almoon.foundation_lib.common.HttpResult
import com.almoon.foundation_lib.interfaces.HttpCallback
import com.almoon.foundation_lib.interfaces.HttpMapFun
import okhttp3.ResponseBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import retrofit2.Call
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var viewModel : TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(TestViewModel::class.java)
        Foundation.bind(this)
        viewModel.getTest().value = 1
        Foundation.getLog().init(false, true, true)
        Foundation.getLog().openAutoUpload(this)
        //Foundation.getLog().e("aaa", "aaa", Exception("aaa"))
        val a = 1 / 0

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
                    Foundation.getLog().j(result!!.getData())
                    val loginSend2 = LoginSend()
                    loginSend2.setEmail("233@233.com")
                    loginSend2.setPassword("233233")
                    val requestBody2 = Foundation.getHttp().getJsonRequestBody(loginSend2)
                    val service2 = Foundation.getHttp().getGsonService("http://47.93.139.52:8000/", RetrofitService::class)
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
            })
            .addCallback(object : HttpCallback<CommonReturn> {
                override fun onSuccess(result: HttpResult<CommonReturn>?) {
                    Log.d("aaa", "2"+ result!!.getData()!!.getMessage())
                }

                override fun onFail(result: ResponseBody) {
                    TODO("Not yet implemented")
                }

                override fun onFail() {
                    TODO("Not yet implemented")
                }

                override fun onFail(errorMessage: String?) {
                    TODO("Not yet implemented")
                }

            })
            .execute()
        Foundation.getPermission().requestPermission(this, 1, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        Foundation.getEvent().postEvent("aaa","ddd")
    }

    @ObserveFun("viewModel.getTest()")
    fun test() {
        Log.d("aaa","aaa")
    }
    @ObserveFun("viewModel.getTest()")
    fun test2() {
        Log.d("aaa","bbb")
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun test3(eventMsg: EventMsg<String, Int>) {
        if ("aaa" == eventMsg.getWhat()) {
            Log.d("aaa", "bbb" + eventMsg.getMsg())
        } else {
            Log.d("aaa", "eee")
        }
    }

}