package com.ncx.usagestatsmanagertest;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iiMedia on 2018/8/23.
 * 获取安装应用异步任务
 */

public class GetInstallAppTask extends AsyncTask<Void, Void, List<AppInfo>> {
    private GetInstallCallback callback;
    private PackageManager pm;

    public GetInstallAppTask(PackageManager pm, GetInstallCallback callback) {
        this.pm = pm;
        this.callback = callback;
    }

    @Override
    protected List<AppInfo> doInBackground(Void... voids) {
        List<PackageInfo> infoList = pm.getInstalledPackages(PackageManager.GET_META_DATA);
        List<AppInfo> appInfoList = new ArrayList<>();
        for (PackageInfo infoList2 : infoList) {
            AppInfo appInfo = new AppInfo(infoList2.packageName
                    + "(" + pm.getApplicationLabel(infoList2.applicationInfo).toString() + ")"
                    , infoList2.versionName
                    , Utils.formatTime(infoList2.firstInstallTime)
                    , Utils.formatTime(infoList2.lastUpdateTime)
                    , pm.getApplicationIcon(infoList2.applicationInfo));
            appInfoList.add(appInfo);
        }
        return appInfoList;
    }

    @Override
    protected void onPostExecute(List<AppInfo> aVoid) {
        super.onPostExecute(aVoid);
        if (aVoid != null) {
            callback.getInstallCallback(aVoid);
        }
    }

    public interface GetInstallCallback {
        void getInstallCallback(List<AppInfo> infoList);
    }
}
