package com.ncx.usagestatsmanagertest

import android.graphics.drawable.Drawable

/**
 * Created by iiMedia on 2018/8/23.
 */
data class AppInfo(
        val name: String,
        val version: String,
        val firstInstallTime: String,
        val lastUpdateTime: String,
        val logo: Drawable
)