package com.almoon.foundation_lib.utils

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * EncryptUtil, you can extend it to use your util
 */
open class EncryptUtil {
    companion object {
        fun encrypt(origin: String?, type: String): String? {
            var result = ""
            if (origin != null && origin.isNotEmpty()) {
                try {
                    val messageDigest =
                        MessageDigest.getInstance(type)
                    messageDigest.update(origin.toByteArray())
                    val byteBuffer = messageDigest.digest()
                    val strHexString = StringBuilder()
                    for (b in byteBuffer) {
                        val hex = Integer.toHexString(0xff and b.toInt())
                        if (hex.length == 1) {
                            strHexString.append('0')
                        }
                        strHexString.append(hex)
                    }
                    result = strHexString.toString()
                } catch (e: NoSuchAlgorithmException) {
                    e.printStackTrace()
                }
            }
            return result
        }
    }

}