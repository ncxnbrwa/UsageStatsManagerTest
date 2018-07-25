package com.ncx.usagestatsmanagertest

import android.annotation.TargetApi
import android.app.usage.UsageStats
import android.content.Context
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.view.*
import java.text.SimpleDateFormat

/**
 * Created by ncx on 2018/7/25.
 */
class RvAdapter(val context: Context, val list: MutableList<UsageStats>) : RecyclerView.Adapter<RvAdapter.Holder>() {

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        holder?.bind(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder
            = Holder(LayoutInflater.from(context).inflate(R.layout.list_item, null))

    override fun getItemCount(): Int = list.size

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: UsageStats) {
            itemView.packageName.text = data.packageName
            itemView.lastUseTime.text = formatTime(data.lastTimeUsed)
            itemView.timeRange.text = "查询范围:${formatTime(data.firstTimeStamp)}--${formatTime(data.lastTimeStamp)}"
            itemView.timeInFore.text = "前台占用时间:${data.totalTimeInForeground/1000}秒"
        }

        fun formatTime(time: Long): String {
            val sdf = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
            return sdf.format(time)
        }

        fun getRange(time:Long):String {
            var str = ""
            if (time >= 60000) {
                str = "${time/60000}分${time/1000}秒"
            }
            return str
        }
    }
}