package com.ncx.usagestatsmanagertest

import android.app.usage.UsageEvents
import android.content.Context
import android.content.pm.PackageManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.event_item.view.*

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
            itemView.eventType.text = "事件类型:${Utils.conversionEventType(event.eventType)}"
            itemView.appLogo.setImageDrawable(pm.getApplicationIcon(event.packageName))
            itemView.occurTime.text = "发生时间:${Utils.formatTime(event.timeStamp)}"
            itemView.className.text = "类名:${event.className}"
        }

    }
}