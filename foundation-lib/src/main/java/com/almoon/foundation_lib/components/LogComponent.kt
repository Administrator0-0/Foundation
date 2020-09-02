package com.almoon.foundation_lib.components

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

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

    /**
     * Init Log's TAG
     */
    fun init(TAG: String) {
        this.TAG = TAG
    }

    /**
     * Reset Log's TAG
     */
    fun reset() {
        this.TAG = "Foundation"
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
        printLog(V, *s)
    }

    /**
     * Print Json
     */
    fun j(jsonStr: String) {
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
            val lines = message.split(LINE_SEPARATOR).toTypedArray()
            printLog(D, *lines)
    }

    /**
     * Common Log
     */
    fun i(msg: String) {
        printLog(I, msg)
    }

    fun d(msg: String) {
        printLog(D, msg)
    }

    fun w(msg: String) {
        printLog(W, msg)
    }

    fun e(msg: String) {
        printLog(E, msg)
    }

    fun v(msg: String) {
        printLog(V, msg)
    }

    fun i(tag: String, msg: String) {
        Log.i(tag, msg)
    }

    fun d(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    fun w(tag: String, msg: String) {
        Log.w(tag, msg)
    }

    fun e(tag: String, msg: String) {
        Log.e(tag, msg)
    }

    fun v(tag: String, msg: String) {
        Log.v(tag, msg)
    }

    fun i(tag: String, msg: String, tr: Throwable) {
        Log.i(tag, msg, tr)
    }

    fun d(tag: String, msg: String, tr: Throwable) {
        Log.d(tag, msg, tr)
    }

    fun w(tag: String, msg: String, tr: Throwable) {
        Log.w(tag, msg, tr)
    }

    fun e(tag: String, msg: String, tr: Throwable) {
        Log.e(tag, msg, tr)
    }

    fun v(tag: String, msg: String, tr: Throwable) {
        Log.v(tag, msg, tr)
    }

    private fun print(type: Char, str: String) {
        when (type) {
            I -> Log.i(TAG, str)
            D -> Log.d(TAG, str)
            E -> Log.e(TAG, str)
            V -> Log.v(TAG, str)
            A -> Log.wtf(TAG, str)
            W -> Log.w(TAG, str)
        }
    }

    private fun printHead(type: Char) {
        print(type, TOP_BORDER)
        print(type, "$HORIZONTAL_DOUBLE_LINE   Thread: " + Thread.currentThread().name)
        print(type, MIDDLE_BORDER)
    }

    private fun printLocation(type: Char, vararg msg: String?) {
        val stack =
            Thread.currentThread().stackTrace
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
        print(type, "$HORIZONTAL_DOUBLE_LINE   Location:")
        sb.append(HORIZONTAL_DOUBLE_LINE)
            .append("   (").append(className.toString()).append(").").append(methodName)
        print(type, sb.toString())
        sb = StringBuilder()
        sb.append(HORIZONTAL_DOUBLE_LINE)
            .append("   (").append(className).append(":").append(lineNumber).append(")")
        print(type, sb.toString())
        print(type, if (msg.isEmpty()) BOTTOM_BORDER else MIDDLE_BORDER)
    }

    private fun printMsg(type: Char, vararg msg: String?) {
        print(type, "$HORIZONTAL_DOUBLE_LINE   Message:")
        for (str in msg) {
            print(type, "$HORIZONTAL_DOUBLE_LINE   $str")
        }
        print(type, BOTTOM_BORDER)
    }

    private fun printLog(type: Char, vararg msg: String?) {
        printHead(type)
        printLocation(type, *msg)
        if (msg.isEmpty()) {
            return
        }
        printMsg(type, *msg)
    }
}