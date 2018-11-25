package com.wxyass.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;

import com.core.ui.loader.LatteLoader;
import com.wxyass.R;
import com.wxyass.base.BaseActivity;

import java.lang.ref.SoftReference;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;


/**
 * MainActivity
 */
public class MainActivity extends BaseActivity {

    MyHandler handler;
    /**
     * 接收子线程消息的 Handler
     */
    public static class MyHandler extends Handler {

        // 软引用
        SoftReference<MainActivity> fragmentRef;

        public MyHandler(MainActivity fragment) {
            fragmentRef = new SoftReference<MainActivity>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity fragment = fragmentRef.get();
            if (fragment == null) {
                return;
            }
            Bundle bundle = msg.getData();

            // 处理UI 变化
            switch (msg.what) {
                case ConstValues.WAIT0:
                    //fragment.showAddProSuc(products, agency);
                    break;
                case ConstValues.WAIT1:
                    // fragment.showAdapter();
                    break;
                case ConstValues.WAIT2:
                    // fragment.showTzAdapter();
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new MyHandler(this);


        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container_main, MainFragment.newInstance());
        }

        LatteLoader.stopLoading();
    }

    // 对返回键进行监听
    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }
}
