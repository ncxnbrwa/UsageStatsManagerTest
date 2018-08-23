package com.ncx.usagestatsmanagertest

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.install_app_item.view.*

/**
 * Created by iiMedia on 2018/8/23.
 * 安装APP适配器
 */
class InstallAppAdapter(val context: Context, val list: MutableList<AppInfo>) : RecyclerView.Adapter<InstallAppAdapter.InstallAppHolder>() {

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): InstallAppHolder {
        return InstallAppHolder(LayoutInflater.from(context).inflate(R.layout.install_app_item, null))
    }

    override fun onBindViewHolder(holder: InstallAppHolder?, position: Int) {
        holder?.bind(list[position])
    }

    class InstallAppHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(appInfo: AppInfo) {
            itemView.packageName.text = appInfo.name
            itemView.installTime.text = "第一次安装时间:${appInfo.firstInstallTime}"
            itemView.lastUpdateTime.text = "上次更新时间:${appInfo.lastUpdateTime}"
            itemView.version.text = "版本号:${appInfo.version}"
            itemView.appLogo.setImageDrawable(appInfo.logo)
        }
    }
}