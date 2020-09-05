package com.almoon.foundation_lib.interfaces

import java.io.Serializable

interface LogcatUploadListener : Serializable {

    fun send2Server(errorMessage: String?)
}