package com.ncx.usagestatsmanagertest;

import android.annotation.SuppressLint;
import android.app.usage.ConfigurationStats;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ncx on 2018/7/25.
 */

public class Main2Activity extends AppCompatActivity {
    private RecyclerView list;
    private Toolbar toolbar;
    long startTime, endTime;
    private String TAG = "UsageStatsManagerTest";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        getForeList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.get_fore:
                getForeList();
                break;
            case R.id.get_config:
                getConfigList();
                break;
            case R.id.get_event:
                getEvent();
                break;
        }
        return true;
    }

    private void getTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        startTime = calendar.getTimeInMillis();
        endTime = System.currentTimeMillis();
        Log.e(TAG, "开始时间:" + startTime);
        Log.e(TAG, "结束时间:" + endTime);
    }

    @SuppressLint("NewApi")
    private void getForeList() {
        getTime();
        UsageStatsManager usm = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> foreList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);
        if (foreList != null && !foreList.isEmpty()) {
            list.setAdapter(new RvAdapter(this, foreList, getPackageManager()));
        } else {
            Toast.makeText(this, "没有前台进程", Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("NewApi")
    private void getConfigList() {
        getTime();
        UsageStatsManager usm = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        List<ConfigurationStats> configList = usm.queryConfigurations(UsageStatsManager.INTERVAL_DAILY
                , startTime, endTime);
        if (configList != null && !configList.isEmpty()) {
            list.setAdapter(new RvConfigAdapter(this, configList, getPackageManager()));
        } else {
            Toast.makeText(this, "没有前台进程", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NewApi")
    private void getEvent() {
        getTime();
        List<UsageEvents.Event> eventList = new ArrayList<>();
        UsageStatsManager usm = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        UsageEvents events = usm.queryEvents(startTime, endTime);
        while (events.hasNextEvent()) {
            UsageEvents.Event event = new UsageEvents.Event();
            events.getNextEvent(event);
            eventList.add(event);
        }
        if (eventList != null && !eventList.isEmpty()) {
            list.setAdapter(new RvEventAdapter(this, eventList, getPackageManager()));
        } else {
            Toast.makeText(this, "没有Event数据", Toast.LENGTH_SHORT).show();
        }
    }

}
