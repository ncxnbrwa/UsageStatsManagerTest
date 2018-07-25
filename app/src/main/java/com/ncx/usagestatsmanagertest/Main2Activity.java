package com.ncx.usagestatsmanagertest;

import android.annotation.SuppressLint;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ncx on 2018/7/25.
 */

public class Main2Activity extends AppCompatActivity {
    private RecyclerView list;
    private String TAG = "UsageStatsManagerTest";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        getForeList();
    }

    @SuppressLint("NewApi")
    private void getForeList() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        long startTime = calendar.getTimeInMillis();
        long endTime = System.currentTimeMillis();
        Log.e(TAG, "开始时间:" + startTime);
        Log.e(TAG, "结束时间:" + endTime);
        UsageStatsManager usm = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> foreList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);
        if (foreList != null && !foreList.isEmpty()) {
            list.setAdapter(new RvAdapter(this, foreList));
        } else {
            Toast.makeText(this, "没有前台进程", Toast.LENGTH_SHORT).show();
        }
    }
}
