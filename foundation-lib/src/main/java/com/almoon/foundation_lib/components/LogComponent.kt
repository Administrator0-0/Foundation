package com.almoon.foundation_lib.components

import android.util.Log
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.PrintWriter
import java.io.StringWriter
import java.net.UnknownHostException

/**
 * LogComponent is designed to easily use Log
 */
class LogComponent {
    private val TOP_LEFT_CORNER = '╔'
    private val BOTTOM_LEFT_CORNER = '╚'
    private val MIDDLE_CORNER = '╟'
    private val HORIZONTAL_DOUBLE_LINE = '║'
    private val DOUBLE_DIVIDER = "════════════════════════════════════════════"
    private val SINGLE_DIVIDER = "────────────────────────────────────────────"
    private val TOP_BORDER = TOP_LEFT_CORNER.toString() + DOUBLE_DIVIDER + DOUBLE_DIVIDER
    private val BOTTOM_BORDER = BOTTOM_LEFT_CORNER.toString() + DOUBLE_DIVIDER + DOUBLE_DIVIDER
    private val MIDDLE_BORDER = MIDDLE_CORNER.toString() + SINGLE_DIVIDER + SINGLE_DIVIDER
    private val I = 'I'
    private val W = 'W'
    private val D = 'D'
    private val E = 'E'
    private val V = 'V'
    private val A = 'A'
    private val LINE_SEPARATOR = System.getProperty("line.separator")
    private val JSON_INDENT = 4
    private var TAG = "Foundation"
    private var threadEnable = true
    private var locationEnable = true
    private var messageEnable = true

    /**
     * Init Log
     */
    fun init(TAG: String) {
        init(TAG, true, locationEnable = true, messageEnable = true)
    }

    fun init(threadEnable: Boolean) {
        init(threadEnable, locationEnable = true, messageEnable = true)
    }

    fun init(threadEnable: Boolean, locationEnable: Boolean) {
        init(threadEnable, locationEnable, messageEnable = true)
    }

    fun init(threadEnable: Boolean, locationEnable: Boolean, messageEnable: Boolean) {
        this.threadEnable = threadEnable
        this.locationEnable = locationEnable
        this.messageEnable = messageEnable
    }

    fun init(TAG: String, threadEnable: Boolean) {
        init(TAG, threadEnable, locationEnable = true, messageEnable = true)
    }

    fun init(TAG: String, threadEnable: Boolean, locationEnable: Boolean) {
        init(TAG, threadEnable, locationEnable, messageEnable = true)
    }

    fun init(TAG: String, threadEnable: Boolean, locationEnable: Boolean, messageEnable: Boolean) {
        this.TAG = TAG
        this.threadEnable = threadEnable
        this.locationEnable = locationEnable
        this.messageEnable = messageEnable
    }

    /**
     * Reset Log
     */
    fun reset() {
        this.TAG = "Foundation"
        this.threadEnable = true
        this.locationEnable = true
        this.messageEnable = true
    }

    /**
     * Print Map
     */
    fun m(map: Map<*, *>) {
        val set: Set<*> = map.entries
        if (set.isEmpty()) {
            printLog(D, "[]")
            return
        }
        val s = arrayOfNulls<String>(set.size)
        for ((i, aSet) in set.withIndex()) {
            val entry = aSet as Map.Entry<*, *>
            s[i] = "${entry.key.toString()} = ${entry.value},"
        }
        printLog(V, null, *s)
    }

    /**
     * Print Json
     */
    fun<T> j(bean: T) {
        val gson = Gson()
        printLog(D, null, *parseJsonStr(gson.toJson(bean)))
    }

    fun j(jsonStr: String) {
        printLog(D, null, *parseJsonStr(jsonStr))
    }

    /**
     * Parse json string to log
     */
    private fun parseJsonStr(jsonStr: String): Array<String> {
        var message: String
        message = try {
            when {
                jsonStr.startsWith("{") -> {
                    val jsonObject = JSONObject(jsonStr)
                    jsonObject.toString(JSON_INDENT)
                }
                jsonStr.startsWith("[") -> {
                    val jsonArray = JSONArray(jsonStr)
                    jsonArray.toString(JSON_INDENT)
                }
                else -> {
                    jsonStr
                }
            }
        } catch (e: JSONException) {
            jsonStr
        }
        message = LINE_SEPARATOR!! + message
        return message.split(LINE_SEPARATOR).toTypedArray()
    }

    /**
     * Common Log
     */
    fun i(msg: String) {
        printLog(I, null, msg)
    }

    fun d(msg: String) {
        printLog(D, null, msg)
    }

    fun w(msg: String) {
        printLog(W, null, msg)
    }

    fun e(msg: String) {
        printLog(E, null, msg)
    }

    fun v(msg: String) {
        printLog(V, null, msg)
    }

    /**
     * Log with TAG
     */
    fun i(tag: String, msg: String) {
        printLog(I, tag, msg)
    }

    fun d(tag: String, msg: String) {
        printLog(D, tag, msg)
    }

    fun w(tag: String, msg: String) {
        printLog(W, tag, msg)
    }

    fun e(tag: String, msg: String) {
        printLog(E, tag, msg)
    }

    fun v(tag: String, msg: String) {
        printLog(V, tag, msg)
    }

    /**
     * Log with Throwable
     */
    fun i(tag: String, msg: String, tr: Throwable) {
        printLog(I, tag, parseThrowable(msg, tr))
    }

    fun d(tag: String, msg: String, tr: Throwable) {
        printLog(D, tag, parseThrowable(msg, tr))
    }

    fun w(tag: String, msg: String, tr: Throwable) {
        printLog(W, tag, parseThrowable(msg, tr))
    }

    fun e(tag: String, msg: String, tr: Throwable) {
        printLog(E, tag, parseThrowable(msg, tr))
    }

    fun v(tag: String, msg: String, tr: Throwable) {
        printLog(V, tag, parseThrowable(msg, tr))
    }

    private fun parseThrowable(msg: String, tr: Throwable): ArrayList<String> {
        val list = ArrayList<String>()
        list.add(msg)
        val trs = getStackTraceString(tr)
        for (str in trs) {
            list.add(str)
        }
        return list
    }

    private fun print(type: Char, tag: String?, str: String) {
        if (tag == null) {
            when (type) {
                I -> Log.i(TAG, str)
                D -> Log.d(TAG, str)
                E -> Log.e(TAG, str)
                V -> Log.v(TAG, str)
                A -> Log.wtf(TAG, str)
                W -> Log.w(TAG, str)
            }
        } else {
            when (type) {
                I -> Log.i(tag, str)
                D -> Log.d(tag, str)
                E -> Log.e(tag, str)
                V -> Log.v(tag, str)
                A -> Log.wtf(tag, str)
                W -> Log.w(tag, str)
            }
        }

    }

    /**
     * Print Thread
     */
    private fun printHead(type: Char, tag: String?) {
        print(type, tag, "$HORIZONTAL_DOUBLE_LINE   Thread: " + Thread.currentThread().name)
    }

    /**
     * Print Location
     */
    private fun printLocation(type: Char, tag: String?) {
        if (threadEnable) {
            print(type, tag, MIDDLE_BORDER)
        }
        val stack = Thread.currentThread().stackTrace
        var i = 0
        for (e in stack) {
            val name = e.className
            if (name != LogComponent::class.java.name) {
                i++
            } else {
                break
            }
        }
        i += 3
        val className = stack[i].fileName
        val methodName = stack[i].methodName
        val lineNumber = stack[i].lineNumber
        var sb = StringBuilder()
        print(type, tag, "$HORIZONTAL_DOUBLE_LINE   Location:")
        sb.append(HORIZONTAL_DOUBLE_LINE)
            .append("   (").append(className.toString()).append(").").append(methodName)
        print(type, tag, sb.toString())
        sb = StringBuilder()
        sb.append(HORIZONTAL_DOUBLE_LINE)
            .append("   (").append(className).append(":").append(lineNumber).append(")")
        print(type, tag, sb.toString())
    }

    /**
     * Print Message
     */
    private fun printMsg(type: Char, tag: String?, vararg msg: String?) {
        if (threadEnable || locationEnable) {
            print(type, tag, MIDDLE_BORDER)
        }
        print(type, tag, "$HORIZONTAL_DOUBLE_LINE   Message:")
        for (str in msg) {
            print(type, tag, "$HORIZONTAL_DOUBLE_LINE   $str")
        }
    }

    /**
     * Print Message
     */
    private fun printMsg(type: Char, tag: String?, msg: List<String>) {
        if (threadEnable || locationEnable) {
            print(type, tag, MIDDLE_BORDER)
        }
        print(type, tag, "$HORIZONTAL_DOUBLE_LINE   Message:")
        for (str in msg) {
            print(type, tag, "$HORIZONTAL_DOUBLE_LINE   $str")
        }
    }

    /**
     * Print Log
     */
    private fun printLog(type: Char, tag: String?, vararg msg: String?) {
        print(type, tag, TOP_BORDER)
        if (threadEnable) {
            printHead(type, tag)
        }
        if (locationEnable) {
            printLocation(type, tag)
        }
        if (messageEnable) {
            if (msg.isEmpty()) {
                return
            }
            printMsg(type, tag, *msg)
        }
        print(type, tag, BOTTOM_BORDER)
    }

    /**
     * Print Log
     */
    private fun printLog(type: Char, tag: String?, msg: ArrayList<String>) {
        print(type, tag, TOP_BORDER)
        if (threadEnable) {
            printHead(type, tag)
        }
        if (locationEnable) {
            printLocation(type, tag)
        }
        if (messageEnable) {
            if (msg.isEmpty()) {
                return
            }
            printMsg(type, tag, msg)
        }
        print(type, tag, BOTTOM_BORDER)
    }

    /**
     * Handy function to get a loggable stack trace from a Throwable
     * @param tr An exception to log
     */
    fun getStackTraceString(tr: Throwable?): Array<String> {
        if (tr == null) {
            return arrayOf("")
        }
        var t = tr
        while (t != null) {
            if (t is UnknownHostException) {
                return arrayOf("")
            }
            t = t.cause
        }
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        tr.printStackTrace(pw)
        pw.flush()
        return sw.toString().split('\n').toTypedArray()
    }
}