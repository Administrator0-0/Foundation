package com.almoon.foundation_lib.utils

import android.content.Context
import android.content.SharedPreferences
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * SPUtil, you can extend it to use your util
 * You are better to change the default filename
 */
open class SPUtil {
    // Default filename
    var filename = "Default"

    fun put(context: Context, key: String?, `object`: Any) {
        val sp = context.getSharedPreferences(
            filename,
            Context.MODE_PRIVATE
        )
        val editor = sp.edit()
        when (`object`) {
            is String -> {
                editor.putString(key, `object`)
            }
            is Int -> {
                editor.putInt(key, `object`)
            }
            is Boolean -> {
                editor.putBoolean(key, `object`)
            }
            is Float -> {
                editor.putFloat(key, `object`)
            }
            is Long -> {
                editor.putLong(key, `object`)
            }
            else -> {
                editor.putString(key, `object`.toString())
            }
        }
        SharedPreferencesCompat.apply(editor)
    }

    operator fun get(context: Context, key: String?, defaultObject: Any): Any? {
        val sp = context.getSharedPreferences(
            filename,
            Context.MODE_PRIVATE
        )
        when (defaultObject) {
            is String -> {
                return sp.getString(key, defaultObject)
            }
            is Int -> {
                return sp.getInt(key, defaultObject)
            }
            is Boolean -> {
                return sp.getBoolean(key, defaultObject)
            }
            is Float -> {
                return sp.getFloat(key, defaultObject)
            }
            is Long -> {
                return sp.getLong(key, defaultObject)
            }
            else -> return null
        }
    }

    fun remove(context: Context, key: String?) {
        val sp = context.getSharedPreferences(
            filename,
            Context.MODE_PRIVATE
        )
        val editor = sp.edit()
        editor.remove(key)
        SharedPreferencesCompat.apply(editor)
    }

    fun clear(context: Context) {
        val sp = context.getSharedPreferences(
            filename,
            Context.MODE_PRIVATE
        )
        val editor = sp.edit()
        editor.clear()
        SharedPreferencesCompat.apply(editor)
    }

    fun contains(context: Context, key: String?): Boolean {
        val sp = context.getSharedPreferences(
            filename,
            Context.MODE_PRIVATE
        )
        return sp.contains(key)
    }

    fun getAll(context: Context): Map<String, *> {
        val sp = context.getSharedPreferences(
            filename,
            Context.MODE_PRIVATE
        )
        return sp.all
    }

    private object SharedPreferencesCompat {
        private val sApplyMethod: Method? = findApplyMethod()

        private fun findApplyMethod(): Method? {
            try {
                val clz: Class<*> = SharedPreferences.Editor::class.java
                return clz.getMethod("apply")
            } catch (e: NoSuchMethodException) {}
            return null
        }

        fun apply(editor: SharedPreferences.Editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor)
                    return
                }
            } catch (e: IllegalArgumentException) {
            } catch (e: InvocationTargetException) {
            } catch (e: IllegalAccessException) {
            }
            editor.commit()
        }
    }
}