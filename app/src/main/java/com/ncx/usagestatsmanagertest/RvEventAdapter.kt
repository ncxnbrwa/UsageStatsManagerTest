package com.ncx.usagestatsmanagertest

import android.app.usage.UsageEvents
import android.content.Context
import android.content.pm.PackageManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.event_item.view.*
import java.text.SimpleDateFormat

/**
 * Created by iiMedia on 2018/7/31.
 * 事件列表
 */
class RvEventAdapter(val context: Context, val list: MutableList<UsageEvents.Event>, val pm: PackageManager)
    : RecyclerView.Adapter<RvEventAdapter.EventHolder>() {

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: EventHolder?, position: Int) {
        holder?.bind(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): EventHolder
            = EventHolder(LayoutInflater.from(context).inflate(R.layout.event_item, null), pm)

    class EventHolder(view: View, val pm: PackageManager) : RecyclerView.ViewHolder(view) {
        fun bind(event: UsageEvents.Event) {
            val packageInfo = pm.getPackageInfo(event.packageName, PackageManager.GET_META_DATA)
            itemView.packageName.text = "${event.packageName}" +
                    "(${pm.getApplicationLabel(packageInfo.applicationInfo)})"
            itemView.eventType.text = "事件类型:${conversionEventType(event.eventType)}"
            itemView.appLogo.setImageDrawable(pm.getApplicationIcon(event.packageName))
            itemView.occurTime.text = "发生时间:${formatTime(event.timeStamp)}"
            itemView.className.text = "类名:${event.className}"
        }

        fun conversionEventType(type: Int): String {
            var eventStr = ""
            when (type) {
                0 -> eventStr = "NONE"
                1 -> eventStr = "MOVE_TO_FOREGROUND" //Activity被置于前台显示时事件
                2 -> eventStr = "MOVE_TO_BACKGROUND" //Activity被置于后台时事件
                3 -> eventStr = "END_OF_DAY" //今天的最后一个记录事件
                4 -> eventStr = "CONTINUE_PREVIOUS_DAY"
                5 -> eventStr = "CONFIGURATION_CHANGE" //系统配置变化的事件
                6 -> eventStr = "SYSTEM_INTERACTION"
                7 -> eventStr = "USER_INTERACTION" //一条通知显示给用户看时，会记录此事件
                8 -> eventStr = "SHORTCUT_INVOCATION"
                9 -> eventStr = "CHOOSER_ACTION"
                else -> eventStr = "NONE"
            }
            return eventStr
        }

        fun formatTime(time: Long): String {
            val sdf = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
            return sdf.format(time)
        }
    }
}