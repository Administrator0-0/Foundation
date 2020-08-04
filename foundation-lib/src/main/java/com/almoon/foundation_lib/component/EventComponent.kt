package com.almoon.foundation_lib.component

import com.almoon.foundation_lib.common.EventMsg
import org.greenrobot.eventbus.EventBus

class EventComponent {
    fun postEvent(what: String, msg: String) {
        EventBus.getDefault().post(EventMsg.getInstance(what, msg))
    }

    fun postEvent(what: String, msg: String, msgInt: Int) {
        EventBus.getDefault().post(EventMsg.getInstance(what, msg, msgInt))
    }

    fun postStickyEvent(what: String, msg: String) {
        EventBus.getDefault().postSticky(EventMsg.getInstance(what, msg))
    }

    fun postStickyEvent(what: String, msg: String, msgInt: Int) {
        EventBus.getDefault().postSticky(EventMsg.getInstance(what, msg, msgInt))
    }
}