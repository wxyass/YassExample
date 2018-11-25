package com.wxyass.base;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.core.initbase.InitActivity;
import com.core.utils.exit.ExitAppUtils;

import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by wxyass on 2018/8/23.
 */

public class BaseActivity extends InitActivity {

    private final static String TAG = "BaseActivity";

    protected static final int LOCKMESSAGEID = 13;

    private long lockTaskDelay = 120 * 60 * 1000;//锁屏时间

    protected static Timer lockTimer = new Timer();
    protected static LockTask lockTask;
    public static boolean isTimeToLock = false;

    final class LockTask extends TimerTask {
        @Override
        public void run() {
            Log.i(TAG, "time down show dialog lock");
            handler.sendEmptyMessage(LOCKMESSAGEID);
        }
    }

    MyHandler handler;

    /**
     * 接收子线程消息的 Handler
     */
    public static class MyHandler extends Handler {

        // 软引用
        SoftReference<BaseActivity> fragmentRef;

        public MyHandler(BaseActivity fragment) {
            fragmentRef = new SoftReference<BaseActivity>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseActivity fragment = fragmentRef.get();
            if (fragment == null) {
                return;
            }

            // 处理UI 变化
            switch (msg.what) {
                case LOCKMESSAGEID:
                    isTimeToLock = true;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        ExitAppUtils.getInstance().createActivity(this);
        lockTask = new LockTask();
        handler = new MyHandler(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        unLockScreen();
    }

    @Override
    protected void onPause() {
        super.onPause();
        lockScreen();// 把之前的锁销毁,创建新锁
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocus = this.getCurrentFocus();
            if (currentFocus != null) {
                IBinder windowToken = currentFocus.getWindowToken();
                imm.hideSoftInputFromWindow(windowToken, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        lockTask.cancel();
        //lockTimer.cancel();
        super.onDestroy();
        ExitAppUtils.getInstance().destroyActivity(this);
    }

    public void unLockScreen() {
        if (isTimeToLock) {
            //isTimeToLock = false;

//            Intent intent = new Intent(this, LockScreenActivity.class);
//            startActivity(intent);

           // Toast.makeText(MyApplication.getAppContext(),"关屏1分钟",Toast.LENGTH_SHORT).show();

            // System.exit(0);
        }
        lockTask.cancel();
        lockTimer.cancel();
        Log.i(TAG, "unlockScreen  将原任务从队列中移除");

    }

    public void lockScreen() {

        if (lockTask != null) {
            Log.i(TAG, "lockScreen 将原任务从队列中移除");
            lockTask.cancel(); //将原任务从队列中移除
        }
        Log.i(TAG, "lockScreen");
        lockTask = new LockTask();
        lockTimer = new Timer();
        lockTimer.schedule(lockTask, lockTaskDelay);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        // 正在运行的程序
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName) && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断程序是否在前台运行
     *
     * @param packageName
     * @return
     */
    public boolean isOpen(String packageName) {
        if (packageName.equals("") | packageName == null)
            return false;
        ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

        List<RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        for (RunningAppProcessInfo runinfo : runningAppProcesses) {
            String pn = runinfo.processName;
            if (pn.equals(packageName) && runinfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
                return true;
        }
        return false;
    }




}
