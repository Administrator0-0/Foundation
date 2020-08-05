package com.almoon.foundation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.almoon.foundation_annotation.ObserveFun
import com.almoon.foundation_lib.Foundation

class MainActivity : AppCompatActivity() {

    lateinit var viewModel : TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(TestViewModel::class.java)
        Foundation.get().bind(this)
        viewModel.getTest().value = 1
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