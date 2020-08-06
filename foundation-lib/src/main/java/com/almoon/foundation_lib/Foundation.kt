package com.almoon.foundation_lib

import com.almoon.foundation_lib.components.HttpComponent
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

        fun getHttp(): HttpComponent {
            return get().httpComponent
        }

        fun getVM(): ViewModelComponent {
            return get().viewModelComponent
        }
    }

    private val httpComponent: HttpComponent = HttpComponent()
    private val viewModelComponent: ViewModelComponent = ViewModelComponent()



}