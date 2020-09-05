package com.almoon.foundation_lib.common

import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import com.almoon.foundation_lib.interfaces.LogcatUploadListener


class LogcatUploadService() : JobIntentService() {

    private val TAG = "LogcatUploadService"
    private var logcatUploadListener: LogcatUploadListener? = null

    override fun onHandleWork(intent: Intent) {
        logcatUploadListener = intent.getSerializableExtra("listener") as LogcatUploadListener?
        if (logcatUploadListener != null) {
            logcatUploadListener!!.send2Server(intent.getStringExtra("stackTraceInfo"))
        } else {
            Log.e(TAG, "LogcatUploadListener should be set to send stackTraceInfo")
        }
    }

}