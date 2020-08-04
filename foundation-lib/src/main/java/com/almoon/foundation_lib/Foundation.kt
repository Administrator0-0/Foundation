package com.almoon.foundation_lib

import android.app.Activity
import androidx.fragment.app.Fragment
import com.almoon.foundation_lib.component.HttpComponent
import com.almoon.foundation_lib.component.ViewModelComponent
import java.lang.reflect.InvocationTargetException

class Foundation private constructor() {
    companion object {
        private var instance: Foundation? = null
            get() {
                if (field == null) {
                    field = Foundation()
                }
                return field
            }
        fun get(): Foundation {
            return instance!!
        }
    }

    private val httpComponent: HttpComponent = HttpComponent()
    private val viewModelComponent: ViewModelComponent = ViewModelComponent()

    fun bind(activity: Activity) {
        val clazz: Class<*> = activity.javaClass
        try {
            val bindClass = Class.forName(clazz.name + "_FoundationBinding")
            val method = bindClass.getMethod("bind", activity.javaClass)
            method.invoke(bindClass.newInstance(), activity)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
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
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        }
    }

    fun getHttp(): HttpComponent {
        return httpComponent
    }


}