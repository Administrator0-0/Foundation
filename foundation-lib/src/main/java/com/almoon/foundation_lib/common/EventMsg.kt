package com.almoon.foundation_lib.common

/**
 * Carry message in event bus
 */
open class EventMsg <T, V> {
    private var what : T
    private var msg : V
    private var msgInt = 0

    constructor(what: T, msg: V) {
        this.what = what
        this.msg = msg
    }
    constructor(what: T, msg: V, msgInt: Int) {
        this.what = what
        this.msg = msg
        this.msgInt = msgInt
    }

    companion object {
        fun<T, V> getInstance(what: T, msg: V): EventMsg<T, V> {
            return EventMsg(what, msg)
        }

        fun<T, V> getInstance(what: T, msg: V, msgInt: Int): EventMsg<T, V> {
            return EventMsg(what, msg, msgInt)
        }
    }

    fun setWhat(what: T) {
        this.what = what
    }

    fun setMsg(msg: V) {
        this.msg = msg
    }

    fun setMsgInt(msgInt: Int) {
        this.msgInt = msgInt
    }

    fun getWhat(): T {
        return what
    }

    fun getMsg(): V {
        return msg
    }

    fun getMsgInt(): Int {
        return msgInt
    }

}