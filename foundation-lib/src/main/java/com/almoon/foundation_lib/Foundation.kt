package com.almoon.foundation_lib

import android.app.Activity
import androidx.fragment.app.Fragment
import com.almoon.foundation_lib.components.EventComponent
import com.almoon.foundation_lib.components.HttpComponent
import com.almoon.foundation_lib.components.PermissionComponent
import com.almoon.foundation_lib.components.ViewModelComponent
import com.almoon.foundation_lib.utils.DateUtil
import com.almoon.foundation_lib.utils.EncryptUtil
import com.almoon.foundation_lib.utils.SPUtil

/**
 * Foundation is designed to promote the speed of development with MVVM
 */
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

        /**
         * More easily init VMComponent for target
         */
        fun bind(target: Any) {
            val a = target.javaClass
            try {
                a.asSubclass(Activity::class.java)
                getVM().bind(target as Activity)
            } catch (e: ClassCastException) {
                try {
                    a.asSubclass(Fragment::class.java)
                    getVM().bind(target as Fragment)
                } catch (e: ClassCastException) {}
            }
        }

        /**
         * More easily init EventComponent for target
         */
        fun register(target: Any) {
            getEvent().register(target)
        }

        /**
         * More easily init VMComponent & EventComponent for target
         */
        fun fullBind(target: Any) {
            getEvent().register(target)
            val a = target.javaClass
            try {
                a.asSubclass(Activity::class.java)
                getVM().bind(target as Activity)
            } catch (e: ClassCastException) {
                try {
                    a.asSubclass(Fragment::class.java)
                    getVM().bind(target as Fragment)
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

        fun getSPUtil(): SPUtil {
            return get().spUtil
        }

        fun getEncryptUtil(): EncryptUtil {
            return get().encryptUtil
        }


        fun getDateUtil(): DateUtil {
            return get().dateUtil
        }

        fun setSPUtil(spUtil: SPUtil) {
            get().spUtil = spUtil
        }

        fun setEncryptUtil(encryptUtil: EncryptUtil) {
            get().encryptUtil = encryptUtil
        }

        fun setDateUtil(dateUtil: DateUtil) {
            get().dateUtil = dateUtil
        }
    }
    private val httpComponent = HttpComponent()
    private val viewModelComponent = ViewModelComponent()
    private val eventComponent = EventComponent()
    private val permissionComponent = PermissionComponent()
    private var spUtil = SPUtil()
    private var encryptUtil = EncryptUtil()
    private var dateUtil = DateUtil()

}