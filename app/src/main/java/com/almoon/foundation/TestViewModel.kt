package com.almoon.foundation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.almoon.foundation_annotation.ObserveFun
import com.almoon.foundation_lib.Foundation
import com.almoon.foundation_lib.common.EventMsg
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class TestViewModel : ViewModel() {
    private var test : MutableLiveData<Int> = MutableLiveData()

    //@ObserveFun("aaa")
    fun getTest(): MutableLiveData<Int> {
        Foundation.bind(this)
        return test
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun test3(eventMsg: EventMsg<String, String>) {
        if ("aaa" == eventMsg.getWhat()) {
            Log.d("aaa", "fff" + eventMsg.getMsg())
        } else {
            Log.d("aaa", "eee")
        }
    }
}