package com.ncx.usagestatsmanagertest

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
class RvEvent2Adapter(val context: Context, val list: MutableList<EventBean>, val pm: PackageManager)
    : RecyclerView.Adapter<RvEvent2Adapter.Event2Holder>() {

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Event2Holder?, position: Int) {
        holder?.bind(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Event2Holder
            = Event2Holder(LayoutInflater.from(context).inflate(R.layout.event_item, null), pm)

    class Event2Holder(view: View, val pm: PackageManager) : RecyclerView.ViewHolder(view) {
        fun bind(event: EventBean) {
            val packageInfo = pm.getPackageInfo(event.packageName, PackageManager.GET_META_DATA)
            itemView.packageName.text = "${event.packageName}" +
                    "(${pm.getApplicationLabel(packageInfo.applicationInfo)})"
            itemView.eventType.text = "事件类型:${event.eventType}"
            itemView.appLogo.setImageDrawable(pm.getApplicationIcon(event.packageName))
            itemView.occurTime.text = "发生时间:"
            itemView.className.text = "类名:${event.className}"
        }

    }
}