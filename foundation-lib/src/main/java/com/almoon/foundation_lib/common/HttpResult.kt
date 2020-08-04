package com.almoon.foundation_lib.common

class HttpResult <T> (data: T) {
    private var data: T? = data

    fun getData(): T? {
        return data
    }

}