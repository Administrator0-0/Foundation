package com.almoon.foundation_lib

import com.almoon.foundation_lib.component.ViewModelComponent
import com.almoon.foundation_lib.component.HttpComponent

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

    fun init() {

    }

    fun getHttp(): HttpComponent {
        return httpComponent
    }


}