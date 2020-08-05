package com.almoon.foundation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.almoon.foundation_annotation.ObserveFun

class TestViewModel : ViewModel() {
    private var test : MutableLiveData<Int> = MutableLiveData()

    //@ObserveFun("aaa")
    fun getTest(): MutableLiveData<Int> {
        return test
    }
}