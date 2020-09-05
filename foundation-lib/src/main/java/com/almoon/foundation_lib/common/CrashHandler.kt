package com.almoon.foundation_lib.common

import android.app.Activity
import android.content.Intent
import android.os.Process
import android.text.TextUtils
import android.util.Log
import com.almoon.foundation_lib.interfaces.LogcatUploadListener
import com.almoon.foundation_lib.utils.DateUtil
import java.io.*
import kotlin.system.exitProcess

class CrashHandler(var context: Activity,
                   var uploadEnable: Boolean,
                   var saveEnable: Boolean,
                   private var path: String,
                   private var time: Int,
                   private var logcatUploadListener: LogcatUploadListener?) : Thread.UncaughtExceptionHandler {
    private val TAG = "CrashHandler"

    override fun uncaughtException(t: Thread, e: Throwable) {
        val stackTraceInfo: String = getStackTraceInfo(e)
        if (uploadEnable) {
            startUploadService(stackTraceInfo)
        }
        if (saveEnable) {
            saveThrowableMessage(stackTraceInfo)
        }
        context.finish()
        Thread.sleep(time.toLong())
        Process.killProcess(Process.myPid())
        exitProcess(0)
    }

    /**
     * Upload StackTrace info to backend
     */
    private fun startUploadService(stackTraceInfo: String) {
        val intent = Intent(context, LogcatUploadService::class.java)
        intent.putExtra("stackTraceInfo", stackTraceInfo)
        intent.putExtra("listener", logcatUploadListener)
        context.startService(intent)
    }


    /**
     * Get StackTrace info
     */
    private fun getStackTraceInfo(throwable: Throwable): String {
        var pw: PrintWriter? = null
        val writer: Writer = StringWriter()
        try {
            pw = PrintWriter(writer)
            throwable.printStackTrace(pw)
        } catch (e: Exception) {
            return ""
        } finally {
            pw?.close()
        }
        return writer.toString()
    }

    /**
     * Save errorMessage to local file
     */
    private fun saveThrowableMessage(errorMessage: String) {
        if (TextUtils.isEmpty(errorMessage)) {
            return
        }
        if (path.isEmpty()) {
            Log.e(TAG, "Store failed, store path is empty")
            return
        }
        val file = File(path)
        if (!file.exists()) {
            val mkdirs = file.mkdirs()
            if (mkdirs) {
                writeStringToFile(errorMessage, file)
            }
        } else {
            val files = file.listFiles()
            if (files != null) {
                for (file1 in files) {
                    file1.delete()
                }
            }
            writeStringToFile(errorMessage, file)
        }
    }

    private fun writeStringToFile(errorMessage: String, file: File) {
        Thread(Runnable {
            var outputStream: FileOutputStream? = null
            try {
                val inputStream = ByteArrayInputStream(errorMessage.toByteArray())
                outputStream =
                    FileOutputStream(File(file, if (DateUtil().getPresentTime() == null) ""
                    else DateUtil().getPresentTime()!!).toString() + ".txt")
                var len: Int
                val bytes = ByteArray(1024)
                while (inputStream.read(bytes).also { len = it } != -1) {
                    outputStream.write(bytes, 0, len)
                }
                outputStream.flush()
                Log.e(TAG, "Write to local file successful：" + file.absolutePath)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }).start()
    }
}