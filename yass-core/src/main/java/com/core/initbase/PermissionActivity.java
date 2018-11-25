package com.core.initbase;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by wxyass on 2018/8/20.
 */

public class PermissionActivity extends SupportActivity {

    // 权限相关 ↓--------------------------------------------------------------------------

    // 用户通过了几项权限
    private int count;

    /**
     * 判断是否有指定的权限
     */
    public boolean hasPermission(String... permissions) {

        for (String permisson : permissions) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), permisson) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 申请指定的权限.
     */
    public void requestPermission(int code, String... permissions) {

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(permissions, code);
        }
        count = 0;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case InitValues.HARDWEAR_CAMERA_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doOpenCamera();
                } else {
                    Toast.makeText(getApplicationContext(), "请先开启相机权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case InitValues.WRITE_READ_EXTERNAL_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doWriteSDCard();
                } else {
                    Toast.makeText(getApplicationContext(), "请先开启读写存储权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case InitValues.LOCAL_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doLocation();
                } else {
                    Toast.makeText(getApplicationContext(), "请先开启定位权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case InitValues.WRITE_LOCAL_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doCameraWriteSD();
                } else {
                    Toast.makeText(getApplicationContext(), "请先开启读写存储权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case InitValues.LOCATION_ALL_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    count++;// 一共3项权限
                    if(count>=3){
                        doDbtThing();
                    }else{
                        Toast.makeText(getApplicationContext(), "请先开启权限", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "请先开启权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void doCameraWriteSD() {

    }

    // 定位
    public void doLocation() {
    }


    // 拍照
    public void doOpenCamera() {

    }

    // 读写SD卡业务逻辑,由具体的子类实现
    public void doWriteSDCard() {

    }

    // 开启所有权限
    public void doDbtThing() {

    }
    // 权限相关 ↑--------------------------------------------------------------------------

}
