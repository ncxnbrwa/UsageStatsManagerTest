package com.ncx.usagestatsmanagertest;

import android.annotation.SuppressLint;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
    private static final String TAG = "UsageStatsManagerTest";

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
            case R.id.get_event:
                getEvent();
                break;
            case R.id.get_install_app:
                getInstallApp();
                break;
        }
        return true;
    }

    private void getInstallApp() {
        //获取安装APP
        PackageManager pm = getPackageManager();
        new GetInstallAppTask(pm, new GetInstallAppTask.GetInstallCallback() {
            @Override
            public void getInstallCallback(List<AppInfo> infoList) {
                Toast.makeText(Main2Activity.this, "获取到" + infoList.size() + "条安装应用信息"
                        , Toast.LENGTH_LONG).show();
                list.setAdapter(new InstallAppAdapter(Main2Activity.this, infoList));
            }
        }).execute();
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

    /**
     * 通过使用UsageStatsManager获取，此方法是ndroid5.0A之后提供的API
     * 必须：
     * 1. 此方法只在android5.0以上有效
     * 2. AndroidManifest中加入此权限<uses-permission xmlns:tools="http://schemas.android.com/tools" android:name="android.permission.PACKAGE_USAGE_STATS"
     * tools:ignore="ProtectedPermissions" />
     * 3. 打开手机设置，点击安全-高级，在有权查看使用情况的应用中，为这个App打上勾
     */
    @SuppressLint("NewApi")
    private void getForeList() {
        getTime();
        UsageStatsManager usm = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> foreList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);
        //判断是否有对应权限
        if (Utils.HasPermission(this)) {
            if (foreList != null && !foreList.isEmpty()) {
                Toast.makeText(this, "获取前台进程成功,共" + foreList.size() + "条数据"
                        , Toast.LENGTH_SHORT).show();
                list.setAdapter(new RvAdapter(this, foreList, getPackageManager()));
            } else {
                Toast.makeText(this, "没有前台进程", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "权限不够\n请打开手机设置，点击安全-高级，在有权查看使用情况的应用中，为这个App开启权限", Toast.LENGTH_LONG).show();
            //跳转设置页
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

    @SuppressLint("NewApi")
    private void getEvent() {
        getTime();
        List<UsageEvents.Event> eventList = new ArrayList<>();
        UsageStatsManager usm = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        UsageEvents events = usm.queryEvents(startTime, endTime);
        while (events.hasNextEvent()) {
            //循环获取所有Event
            UsageEvents.Event event = new UsageEvents.Event();
            events.getNextEvent(event);
            eventList.add(event);
        }
        if (eventList != null && !eventList.isEmpty()) {
            List<EventBean> eventBeanList = new ArrayList<>();
            int count = 1;
            for (int i = 0; i < eventList.size(); i++) {
                UsageEvents.Event eventi = eventList.get(i);
                count = 1;
                if (eventi.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND ||
                        eventi.getEventType() == UsageEvents.Event.MOVE_TO_BACKGROUND) {
                    for (int j = i + 1; j < eventList.size(); j++) {
                        if (!TextUtils.isEmpty(eventi.getClassName()) && !TextUtils.isEmpty(eventList.get(j).getClassName())) {
                            if (eventList.get(j).getClassName().equals(eventi.getClassName()) &&
                                    eventList.get(j).getEventType() == eventi.getEventType()) {
                                count++;
                                eventList.remove(j);
                                j--;
                            }
                        }
                    }
                    eventBeanList.add(new EventBean(eventi.getPackageName(), Utils.conversionEventType(eventi.getEventType()),
                            TextUtils.isEmpty(eventi.getClassName()) ? "" : eventi.getClassName(), count));
                }
            }
            Toast.makeText(this, "获取Event数据成功,共" + eventBeanList.size() + "条数据"
                    , Toast.LENGTH_SHORT).show();
            list.setAdapter(new RvEvent2Adapter(this, eventBeanList, getPackageManager()));
//            list.setAdapter(new RvEventAdapter(this, eventList, getPackageManager()));
        } else {
            Toast.makeText(this, "没有Event数据", Toast.LENGTH_SHORT).show();
        }
    }

}
