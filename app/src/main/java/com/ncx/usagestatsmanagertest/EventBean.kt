package com.ncx.usagestatsmanagertest

/**
 * Created by iiMedia on 2018/8/9.
 */
data class EventBean(
        val packageName: String,
        val eventType: String,
        val className: String,
        val eventCount: Int
)