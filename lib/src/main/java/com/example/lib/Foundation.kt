package com.example.lib

class Foundation private constructor(){
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

    fun init() {

    }
}