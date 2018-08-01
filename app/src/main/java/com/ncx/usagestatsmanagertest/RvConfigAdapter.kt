package com.ncx.usagestatsmanagertest

import android.app.usage.ConfigurationStats
import android.content.Context
import android.content.pm.PackageManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.configlist_item.view.*

/**
 * Created by ncx on 2018/7/26.
 */
class RvConfigAdapter(val context: Context, val configList: MutableList<ConfigurationStats>
                      , val pm: PackageManager)
    : RecyclerView.Adapter<RvConfigAdapter.ConfigHolder>() {

    override fun onBindViewHolder(holder: ConfigHolder?, position: Int) {
        holder?.bind(configList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ConfigHolder
            = ConfigHolder(LayoutInflater.from(context).inflate(R.layout.configlist_item, null))

    override fun getItemCount(): Int = configList.size

    class ConfigHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: ConfigurationStats) {
            itemView.activeCount.text = "激活次数:${data.activationCount}"
        }
    }
}