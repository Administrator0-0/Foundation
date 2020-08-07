package com.almoon.foundation_lib.components

import com.almoon.foundation_lib.common.EventMsg
import org.greenrobot.eventbus.EventBus

/**
 * EventComponent is designed to easily use EventBus
 */
class EventComponent {
    fun register(any: Any) {
        EventBus.getDefault().register(any)
    }

    fun<T, V> postEvent(what: T, msg: V) {
        EventBus.getDefault().post(EventMsg.getInstance(what, msg))
    }

    fun<T, V> postEvent(what: T, msg: V, msgInt: Int) {
        EventBus.getDefault().post(EventMsg.getInstance(what, msg, msgInt))
    }

    fun<T, V> postStickyEvent(what: T, msg: V) {
        EventBus.getDefault().postSticky(EventMsg.getInstance(what, msg))
    }

    fun<T, V> postStickyEvent(what: T, msg: V, msgInt: Int) {
        EventBus.getDefault().postSticky(EventMsg.getInstance(what, msg, msgInt))
    }
}