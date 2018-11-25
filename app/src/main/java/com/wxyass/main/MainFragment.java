package com.wxyass.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.core.event.TabSelectedEvent;
import com.core.ui.loader.LatteLoader;
import com.core.view.BottomBar;
import com.core.view.BottomBarTab;
import com.wxyass.R;
import com.wxyass.base.BaseFragment;
import com.wxyass.fragment.FifthFragment;
import com.wxyass.fragment.FirstFragment;
import com.wxyass.fragment.FourthFragment;
import com.wxyass.fragment.SecondFragment;
import com.wxyass.fragment.ThirdFragment;

import java.lang.ref.SoftReference;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * 测试 跳转Fragment
 *
 * Created by wxyass on 2018/8/17.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener, View.OnTouchListener {

    private static final int REQ_MSG = 10;

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOUTH = 3;
    public static final int FIVE = 4;

    private SupportFragment[] mFragments = new SupportFragment[5];

    private BottomBar mBottomBar;

    private AppCompatImageView mMemoImg;

    // 用于计算客情备忘录的位置
    private int screenWidth, screenHeight;
    private int lastX, lastY;
    private float visitMemoIvXY;


    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public static MainFragment newInstance(Bundle bundle) {
        Bundle args = bundle;
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    MyHandler handler;

    /**
     * 接收子线程消息的 Handler
     */
    public static class MyHandler extends Handler {

        // 软引用
        SoftReference<MainFragment> fragmentRef;

        public MyHandler(MainFragment fragment) {
            fragmentRef = new SoftReference<MainFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            MainFragment fragment = fragmentRef.get();
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

    public MainFragment() {
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
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //SupportFragment firstFragment = findChildFragment(YdFirstLeftFragment.class);
        SupportFragment firstFragment = findChildFragment(FirstFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = FirstFragment.newInstance();
            mFragments[SECOND] = SecondFragment.newInstance();
            mFragments[THIRD] = ThirdFragment.newInstance();
            mFragments[FOUTH] = FourthFragment.newInstance();
            mFragments[FIVE] = FifthFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_tab_container_main, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOUTH],
                    mFragments[FIVE]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findChildFragment(SecondFragment.class);
            mFragments[THIRD] = findChildFragment(ThirdFragment.class);
            mFragments[FOUTH] = findChildFragment(FourthFragment.class);
            mFragments[FIVE] = findChildFragment(FifthFragment.class);
        }

        // 获取屏幕长宽
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - 50;
    }

    private void initView(View view) {
        mBottomBar = (BottomBar) view.findViewById(R.id.bottomBar_dbtplus);

        mBottomBar
                .addItem(new BottomBarTab(_mActivity, R.drawable.tab_ic_visit_up, getString(R.string.msg)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.tab_ic_operation_up, getString(R.string.discover)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.tab_ic_sign_up, getString(R.string.colckingin)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.tab_ic_chat_up, getString(R.string.groupchat)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.tab_ic_mine_up, getString(R.string.more)));

        // 模拟未读消息
        // mBottomBar.getItem(FIRST).setUnreadCount(9);

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);

                /*BottomBarTab tab = mBottomBar.getItem(FIRST);
                if (position == FIRST) {
                    tab.setUnreadCount(0);
                } else {
                    tab.setUnreadCount(tab.getUnreadCount() + 1);
                }*/
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                // 在FirstPagerFragment,FirstHomeFragment中接收, 因为是嵌套的Fragment
                // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                EventBusActivityScope.getDefault(_mActivity).post(new TabSelectedEvent(position));
            }
        });

        mMemoImg = (AppCompatImageView) view.findViewById(R.id.yd_bt_thingtoday);
        mMemoImg.setOnClickListener(this);
        mMemoImg.setOnTouchListener(this);
    }

    // 加载数据
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // initData();
    }

    private void initData() {
        handler = new MyHandler(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();

                // 获取客户备忘的位置数据
                visitMemoIvXY = v.getLeft() + v.getTop();
                break;

            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;
                int left = v.getLeft() + dx;
                int top = v.getTop() + dy;
                int right = v.getRight() + dx;
                int bottom = v.getBottom() + dy;
                if (left < 0) {
                    left = 0;
                    right = left + v.getWidth();
                }
                if (right > screenWidth) {
                    right = screenWidth;
                    left = right - v.getWidth();
                }
                if (top < 0) {
                    top = 0;
                    bottom = top + v.getHeight();
                }
                if (bottom > screenHeight) {
                    bottom = screenHeight;
                    top = bottom - v.getHeight();
                }
                v.layout(left, top, right, bottom);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;

            default:
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yd_bt_thingtoday:// 客情
                if (visitMemoIvXY == (mMemoImg.getLeft() + mMemoImg.getTop())) {
                    Toast.makeText(_mActivity,"滚",Toast.LENGTH_SHORT).show();
                }
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
        initData();
        LatteLoader.stopLoading();
    }
}
