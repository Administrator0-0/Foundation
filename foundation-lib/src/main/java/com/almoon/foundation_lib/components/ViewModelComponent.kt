package com.almoon.foundation_lib.components

import android.app.Activity
import android.util.Log
import androidx.fragment.app.Fragment
import java.lang.reflect.InvocationTargetException

class ViewModelComponent {
    private val TAG = "ViewModelComponent"

    fun bind(activity: Activity) {
        val clazz: Class<*> = activity.javaClass
        try {
            val bindClass = Class.forName(clazz.name + "_FoundationBinding")
            val method = bindClass.getMethod("bind", activity.javaClass)
            method.invoke(bindClass.newInstance(), activity)
        } catch (e: ClassNotFoundException) {
            Log.e(TAG, "error: don't have any observe function in " + clazz.name)
        } catch (e: NoSuchMethodException) {
            Log.e(TAG, "error: generate error " + clazz.name + "_FoundationBinding")
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        }
    }

    fun bind(fragment: Fragment) {
        val clazz: Class<*> = fragment.javaClass
        try {
            val bindClass = Class.forName(clazz.name + "_FoundationBinding")
            val method = bindClass.getMethod("bind", fragment.javaClass)
            method.invoke(bindClass.newInstance(), fragment)
        } catch (e: ClassNotFoundException) {
            Log.e(TAG, "error: don't have any observe function in " + clazz.name)
        } catch (e: NoSuchMethodException) {
            Log.e(TAG, "error: generate error " + clazz.name + "_FoundationBinding")
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        }
    }
}