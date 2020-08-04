package com.almoon.foundation_lib.common

class EventMsg {
    private var what = ""
    private var msg = ""
    private var msgInt = 0

    constructor(what: String, msg: String) {
        this.what = what
        this.msg = msg
    }
    constructor(what: String, msg: String, msgInt: Int) {
        this.what = what
        this.msg = msg
        this.msgInt = msgInt
    }

    companion object {
        fun getInstance(what: String, msg: String): EventMsg {
            return EventMsg(what, msg)
        }

        fun getInstance(what: String, msg: String, msgInt: Int): EventMsg {
            return EventMsg(what, msg, msgInt)
        }
    }

    fun setWhat(what: String) {
        this.what = what
    }

    fun setMsg(msg: String) {
        this.msg = msg
    }

    fun setMsgInt(msgInt: Int) {
        this.msgInt = msgInt
    }

    fun getWhat(): String {
        return what
    }

    fun getMsg(): String {
        return msg
    }

    fun getMsgInt(): Int {
        return msgInt
    }

}