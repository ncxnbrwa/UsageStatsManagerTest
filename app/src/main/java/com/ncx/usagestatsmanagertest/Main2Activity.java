package com.ncx.usagestatsmanagertest;

import android.annotation.SuppressLint;
import android.app.usage.ConfigurationStats;
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
            list.setAdapter(new RvAdapter(this, foreList,getPackageManager()));
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


    //比较两个应用程序的启动次数和运行时间
//    public final int compare(ApplicationInfo a, ApplicationInfo b) {
//        ComponentName aName = a.intent.getComponent();
//        ComponentName bName = b.intent.getComponent();
//        int aLaunchCount, bLaunchCount;
//        long aUseTime, bUseTime;
//        int result = 0;
//        try {
//            // 获得ServiceManager类
//            Class<?> ServiceManager = Class.forName("android.os.ServiceManager");
//            // 获得ServiceManager的getService方法
//            Method getService = ServiceManager.getMethod("getService", java.lang.String.class);
//            // 调用getService获取RemoteService
//            Object oRemoteService = getService.invoke(null, "usagestats");
//            // 获得IUsageStats.Stub类
//            Class<?> cStub = Class.forName("com.android.internal.app.IUsageStats$Stub");
//            // 获得asInterface方法
//            Method asInterface = cStub.getMethod("asInterface", android.os.IBinder.class);
//            // 调用asInterface方法获取IUsageStats对象
//            Object oIUsageStats = asInterface.invoke(null, oRemoteService);
//            // 获得getPkgUsageStats(ComponentName)方法
//            Method getPkgUsageStats = oIUsageStats.getClass().getMethod("getPkgUsageStats", ComponentName.class);
//            // 调用getPkgUsageStats 获取PkgUsageStats对象
//            Object aStats = getPkgUsageStats.invoke(oIUsageStats, aName);
//            Object bStats = getPkgUsageStats.invoke(oIUsageStats, bName);
//            // 获得PkgUsageStats类
//            Class<?> PkgUsageStats = Class.forName("com.android.internal.os.PkgUsageStats");
//            aLaunchCount = PkgUsageStats.getDeclaredField("launchCount").getInt(aStats);
//            bLaunchCount = PkgUsageStats.getDeclaredField("launchCount").getInt(bStats);
//            aUseTime = PkgUsageStats.getDeclaredField("usageTime").getLong(aStats);
//            bUseTime = PkgUsageStats.getDeclaredField("usageTime").getLong(bStats);
//            if ((aLaunchCount > bLaunchCount) || ((aLaunchCount == bLaunchCount) && (aUseTime > bUseTime)))
//                result = 1;
//            else if ((aLaunchCount < bLaunchCount) || ((aLaunchCount == bLaunchCount) && (aUseTime < bUseTime)))
//                result = -1;
//            else {
//                result = 0;
//            }
//        } catch (Exception e) {
//            Log.e("###", e.toString(), e);
//        }
//        return result;
//    }

}
