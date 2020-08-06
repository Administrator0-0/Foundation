package com.almoon.foundation_lib

import android.app.Activity
import androidx.fragment.app.Fragment
import com.almoon.foundation_lib.components.EventComponent
import com.almoon.foundation_lib.components.HttpComponent
import com.almoon.foundation_lib.components.PermissionComponent
import com.almoon.foundation_lib.components.ViewModelComponent

class Foundation private constructor() {
    companion object {
        private var instance: Foundation? = null
            get() {
                if (field == null) {
                    field = Foundation()
                }
                return field
            }
        private fun get(): Foundation {
            return instance!!
        }

        fun bind(any: Any) {
            getEvent().register(any)
            val a = any.javaClass
            try {
                a.asSubclass(Activity::class.java)
                getVM().bind(any as Activity)
            } catch (e: ClassCastException) {
                try {
                    a.asSubclass(Fragment::class.java)
                    getVM().bind(any as Fragment)
                } catch (e: ClassCastException) {}
            }
        }

        fun getHttp(): HttpComponent {
            return get().httpComponent
        }

        fun getVM(): ViewModelComponent {
            return get().viewModelComponent
        }

        fun getEvent(): EventComponent {
            return get().eventComponent
        }

        fun getPermission(): PermissionComponent {
            return get().permissionComponent
        }
    }
    private var httpComponent: HttpComponent = HttpComponent()
    private val viewModelComponent: ViewModelComponent = ViewModelComponent()
    private val eventComponent: EventComponent = EventComponent()
    private val permissionComponent: PermissionComponent = PermissionComponent()



}