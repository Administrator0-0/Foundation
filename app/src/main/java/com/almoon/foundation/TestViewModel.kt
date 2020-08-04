package com.almoon.foundation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TestViewModel : ViewModel() {
    private var test : MutableLiveData<Int> = MutableLiveData()

    fun getTest(): MutableLiveData<Int> {
        return test
    }
}