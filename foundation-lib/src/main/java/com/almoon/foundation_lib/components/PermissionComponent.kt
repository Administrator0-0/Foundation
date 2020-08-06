package com.almoon.foundation_lib.components

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

class PermissionComponent {
    private val TAG: String = "RequestPermission"
    private lateinit var builder: PermissionRequest.Builder

    fun getBuilder(): PermissionRequest.Builder {
        return builder
    }

    fun checkPermission(context: Context, vararg strings:String): Boolean {
        return EasyPermissions.hasPermissions(context, *strings)
    }

    fun requestPermission(context: Activity, requestCode: Int, vararg strings:String) {
        if (!checkPermission(context, *strings)) {
            builder = PermissionRequest.Builder(context, requestCode, *strings)
            EasyPermissions.requestPermissions(builder.build())
        }
    }

    fun requestPermission (
        context: Fragment,
        negative: String,
        positive : String,
        rationale: String,
        requestCode: Int,
        vararg strings:String
    ) {
        if (context.context == null) {
            Log.e(TAG, "error: context is null")
            return
        }
        if (!checkPermission(context.context!!, *strings)) {
            builder = PermissionRequest.Builder(context, requestCode, *strings)
            builder.setNegativeButtonText(negative)
                .setPositiveButtonText(positive)
                .setRationale(rationale)
            EasyPermissions.requestPermissions(builder.build())
        }
    }

    fun requestPermission (
        context: Fragment,
        negative: Int,
        positive : Int,
        rationale: Int,
        requestCode: Int,
        vararg strings:String
    ) {
        if (context.context == null) {
            Log.e(TAG, "error: context is null")
            return
        }
        if (!checkPermission(context.context!!, *strings)) {
            builder = PermissionRequest.Builder(context, requestCode, *strings)
            builder.setNegativeButtonText(negative)
                .setPositiveButtonText(positive)
                .setRationale(rationale)
            EasyPermissions.requestPermissions(builder.build())
        }
    }
}