package com.ncx.usagestatsmanagertest;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.text.SimpleDateFormat;

/**
 * Created by iiMedia on 2018/8/1.
 */

public class Utils {

    //判断是否有安全权限
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean HasPermission(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            //检查get_usage_stats功能是否允许
            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
            return (mode == AppOpsManager.MODE_ALLOWED);
        } catch (PackageManager.NameNotFoundException e) {
            return true;
        }
    }

    public static String formatTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return sdf.format(time);
    }

    // Event事件参数转化
    public static String conversionEventType(int type) {
        String typeStr = "";
        switch (type) {
            case 0:
                typeStr = "NONE";
                break;
            case 1:
                typeStr = "MOVE_TO_FOREGROUND"; //Activity被置于前台显示时事件
                break;
            case 2:
                typeStr = "MOVE_TO_BACKGROUND"; //Activity被置于后台时事件
                break;
            case 3:
                typeStr = "END_OF_DAY"; //今天的最后一个记录事件
                break;
            case 4:
                typeStr = "CONTINUE_PREVIOUS_DAY";
                break;
            case 5:
                typeStr = "CONFIGURATION_CHANGE"; //系统配置变化的事件
                break;
            case 6:
                typeStr = "SYSTEM_INTERACTION";
                break;
            case 7:
                typeStr = "USER_INTERACTION"; //一条通知显示给用户看时，会记录此事件
                break;
            case 8:
                typeStr = "SHORTCUT_INVOCATION";
                break;
            case 9:
                typeStr = "CHOOSER_ACTION";
                break;
            default:
                typeStr = "NONE";
                break;
        }
        return typeStr;
    }
}
