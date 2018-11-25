package com.wxyass.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.core.ui.loader.LatteLoader;
import com.wxyass.R;
import com.wxyass.base.BaseFragment;
import com.wxyass.main.ConstValues;

import java.lang.ref.SoftReference;


/**
 * 测试 跳转Fragment
 *
 * Created by wxyass on 2018/8/17.
 */
public class FirstFragment extends BaseFragment implements View.OnClickListener {


    public static FirstFragment newInstance() {
        Bundle args = new Bundle();
        FirstFragment fragment = new FirstFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public static FirstFragment newInstance(Bundle bundle) {
        Bundle args = bundle;
        FirstFragment fragment = new FirstFragment();
        fragment.setArguments(args);
        return fragment;
    }

    MyHandler handler;

    /**
     * 接收子线程消息的 Handler
     */
    public static class MyHandler extends Handler {

        // 软引用
        SoftReference<FirstFragment> fragmentRef;

        public MyHandler(FirstFragment fragment) {
            fragmentRef = new SoftReference<FirstFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            FirstFragment fragment = fragmentRef.get();
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

    public FirstFragment() {
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
        View view = inflater.inflate(R.layout.fragment_first,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {

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
            /*case R.id.third_rl_1://
                ((MainFragment) getParentFragment()).start(new BasisViewFragment());
                break;*/
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
