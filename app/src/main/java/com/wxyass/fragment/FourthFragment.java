package com.wxyass.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.core.ui.loader.LatteLoader;
import com.core.web.WebDelegateImpl;
import com.wxyass.R;
import com.wxyass.base.BaseFragment;
import com.wxyass.fourth.HelloFragment1;
import com.wxyass.fourth.WebExampleFragment;
import com.wxyass.low.LowActivity;
import com.wxyass.low.LowFragment;
import com.wxyass.main.ConstValues;
import com.wxyass.main.MainFragment;

import java.lang.ref.SoftReference;


/**
 * 测试 跳转Fragment
 *
 * Created by wxyass on 2018/8/17.
 */
public class FourthFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout mFourth_rl_01;
    private LinearLayout mFourth_rl_02;
    private LinearLayout mFourth_rl_03;
    private LinearLayout mFourth_rl_04;
    private LinearLayout mFourth_rl_05;
    private LinearLayout mFourth_rl_06;

    public static FourthFragment newInstance() {
        Bundle args = new Bundle();
        FourthFragment fragment = new FourthFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public static FourthFragment newInstance(Bundle bundle) {
        Bundle args = bundle;
        FourthFragment fragment = new FourthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    MyHandler handler;

    /**
     * 接收子线程消息的 Handler
     */
    public static class MyHandler extends Handler {

        // 软引用
        SoftReference<FourthFragment> fragmentRef;

        public MyHandler(FourthFragment fragment) {
            fragmentRef = new SoftReference<FourthFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            FourthFragment fragment = fragmentRef.get();
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

    public FourthFragment() {
    }

    // 接收传递过来的参数
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 初始化控件
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        mFourth_rl_01 = (LinearLayout) view.findViewById(R.id.fourth_rl_01);
        mFourth_rl_02 = (LinearLayout) view.findViewById(R.id.fourth_rl_02);
        mFourth_rl_03 = (LinearLayout) view.findViewById(R.id.fourth_rl_03);
        mFourth_rl_04 = (LinearLayout) view.findViewById(R.id.fourth_rl_04);
        mFourth_rl_05 = (LinearLayout) view.findViewById(R.id.fourth_rl_05);
        mFourth_rl_06 = (LinearLayout) view.findViewById(R.id.fourth_rl_06);

        mFourth_rl_01.setOnClickListener(this);
        mFourth_rl_02.setOnClickListener(this);
        mFourth_rl_03.setOnClickListener(this);
        mFourth_rl_04.setOnClickListener(this);
        mFourth_rl_05.setOnClickListener(this);
        mFourth_rl_06.setOnClickListener(this);

    }

    // 加载数据
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        /*if(findChildFragment(LowFragment.class)==null){
            // loadRootFragment(R.id.fl_first_container, FirstHomeFragment.newInstance());
        }*/
        // initData();
    }

    private void initData() {
        handler = new MyHandler(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fourth_rl_01:// Fragment跳转
                LatteLoader.showLoading(_mActivity, true);
                ((MainFragment) getParentFragment()).start(HelloFragment1.newInstance());
                break;
            case R.id.fourth_rl_02:// 2
                StringBuffer buffer = new StringBuffer();
                buffer.append("index.html");
                final WebDelegateImpl index = WebDelegateImpl.create(buffer.toString());
                ((MainFragment) getParentFragment()).start(index);
                break;
            case R.id.fourth_rl_03:// Fragment中嵌套WebView
                ((MainFragment) getParentFragment()).start(new WebExampleFragment());
                break;
            case R.id.fourth_rl_04:// 整体Fragment
                final WebDelegateImpl delegate = WebDelegateImpl.create("http://wxyass.com/");
                ((MainFragment) getParentFragment()).start(delegate);
                break;
            case R.id.fourth_rl_05:// 退出系统
                System.exit(0);
                break;
            case R.id.fourth_rl_06:// Activity跳转
                _mActivity.startActivity(new Intent(_mActivity, LowActivity.class));
                break;
        }
    }

    // 不可见时触发
    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();

        String sexs = "sdsdf";
    }

    // 可见时触发
    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        // 每次都重新加载数据
        // 每次都重新加载数据
        initData();
        LatteLoader.stopLoading();
    }
}
