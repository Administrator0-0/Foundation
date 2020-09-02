package com.almoon.foundation_lib.common

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Process
import android.text.TextUtils
import android.util.Log
import com.almoon.foundation_lib.utils.DateUtil
import retrofit2.Call
import java.io.*

class CrashHandler : Thread.UncaughtExceptionHandler {
    var context: Context? = null
    var call: Call<*>? = null
    var path = ""

    fun MyCrashHandler(context: Context?) {
        this.context = context
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        Log.e("CrashHandler", "Thread = ${t.name}Throwable = ${e.message}".trimIndent())
        val stackTraceInfo: String = getStackTraceInfo(e)
        Log.e("stackTraceInfo", stackTraceInfo)
        startUploadService(stackTraceInfo)
        saveThrowableMessage(stackTraceInfo)
        Process.killProcess(Process.myPid())
    }

    /**
     * Upload StackTrace info to backend
     */
    private fun startUploadService(stackTraceInfo: String) {
        var appVersion = ""
        val manager = context!!.packageManager
        try {
            val info = manager.getPackageInfo(context!!.packageName, 0)
            appVersion = "Android-" + info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        val intent = Intent(context, LogcatUploadService::class.java)
        intent.putExtra("appVersion", appVersion)
        intent.putExtra("stackTraceInfo", stackTraceInfo)
        context!!.startService(intent)
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
                Log.e("CrashHandler", "Write to local file successful：" + file.absolutePath)
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