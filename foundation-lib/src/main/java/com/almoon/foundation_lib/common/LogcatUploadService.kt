package com.almoon.foundation_lib.common

import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import com.almoon.foundation_lib.interfaces.LogcatUploadListener


class LogcatUploadService : JobIntentService() {

    private val TAG = "LogcatUploadService"
    private var logcatUploadListener: LogcatUploadListener? = null

    fun setListener(logcatUploadListener: LogcatUploadListener) {
        this.logcatUploadListener = logcatUploadListener
    }

    override fun onHandleWork(intent: Intent) {
        if (logcatUploadListener != null) {
            logcatUploadListener!!.send2Server(intent.getStringExtra("stackTraceInfo"))
        } else {
            Log.e(TAG, "LogcatUploadListener should be set to send stackTraceInfo")
        }
    }

}