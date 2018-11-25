package com.wxyass.low;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.RelativeLayout;

import com.core.ui.loader.LatteLoader;
import com.wxyass.R;
import com.wxyass.base.BaseActivity;
import com.wxyass.main.ConstValues;

import java.lang.ref.SoftReference;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;


/**
 * 测试 跳转Activity
 */
public class LowActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout backBtn;
    private RelativeLayout confirmBtn;
    private AppCompatTextView confirmTv;
    private AppCompatTextView backTv;
    private AppCompatTextView titleTv;

    MyHandler handler;

    /**
     * 接收子线程消息的 Handler
     */
    public static class MyHandler extends Handler {

        // 软引用
        SoftReference<LowActivity> fragmentRef;

        public MyHandler(LowActivity fragment) {
            fragmentRef = new SoftReference<LowActivity>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            LowActivity fragment = fragmentRef.get();
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
        setContentView(R.layout.activity_low);
        handler = new MyHandler(this);

        backBtn = (RelativeLayout) findViewById(R.id.top_navigation_rl_back);
        confirmBtn = (RelativeLayout) findViewById(R.id.top_navigation_rl_confirm);
        confirmTv = (AppCompatTextView) findViewById(R.id.top_navigation_bt_confirm);
        backTv = (AppCompatTextView) findViewById(R.id.top_navigation_bt_back);
        titleTv = (AppCompatTextView) findViewById(R.id.top_navigation_tv_title);
        confirmBtn.setVisibility(View.VISIBLE);
        backBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);

        LatteLoader.stopLoading();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_navigation_rl_back://
                LowActivity.this.finish();
                break;
        }
    }

    // 对返回键进行监听
    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }
}
