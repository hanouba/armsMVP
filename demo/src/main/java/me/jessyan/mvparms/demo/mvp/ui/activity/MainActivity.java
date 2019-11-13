package me.jessyan.mvparms.demo.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.widget.FrameLayout;

import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.app.base.BaseSupportActivity;
import me.jessyan.mvparms.demo.mvp.ui.fragment.DeviceFragment;
import me.jessyan.mvparms.demo.mvp.ui.fragment.HomeFragment;
import me.jessyan.mvparms.demo.mvp.ui.fragment.PersenFragment;
import me.jessyan.mvparms.demo.mvp.ui.fragment.VideoFragment;
import me.jessyan.mvparms.demo.mvp.ui.fragment.WorkFragment;
import me.jessyan.mvparms.demo.mvp.ui.widget.bottombar.BottomBar;
import me.jessyan.mvparms.demo.mvp.ui.widget.bottombar.BottomBarTab;
import me.yokeyword.fragmentation.ISupportFragment;

public class MainActivity extends BaseSupportActivity {

    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;
    @BindView(R.id.fragment_contain)
    FrameLayout fragmentContain;
    @BindView(R.id.navigation)
    NavigationView navigation;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    private BottomBarTab homeTab;
    private BottomBarTab categoryTab;
    private BottomBarTab cartTab;
    private BottomBarTab findTab;
    private BottomBarTab selfTab;

    private ISupportFragment[] mFragments = new ISupportFragment[5];

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initBottomBar();
    }

    /**
     * 底部状态栏
     */
    private void initBottomBar() {
        ISupportFragment recommendFragment = findFragment(HomeFragment.class);
        if (recommendFragment == null) {
            mFragments[0] = new HomeFragment();
            mFragments[1] = new WorkFragment();
            mFragments[2] = new DeviceFragment();
            mFragments[3] = new VideoFragment();
            mFragments[4] = new PersenFragment();
            loadMultipleRootFragment(R.id.fragment_contain, 0, mFragments);
        } else {
            mFragments[0] = findFragment(HomeFragment.class);
            mFragments[1] = findFragment(WorkFragment.class);
            mFragments[2] = findFragment(DeviceFragment.class);
            mFragments[3] = findFragment(VideoFragment.class);
            mFragments[4] = findFragment(PersenFragment.class);
        }


        homeTab = new BottomBarTab(mContext, R.mipmap.icon_navigation_home, "首页");
        categoryTab = new BottomBarTab(mContext, R.mipmap.icon_navigation_category, "工单");
        cartTab = new BottomBarTab(mContext, R.mipmap.icon_navigation_cart, "设备");
        findTab = new BottomBarTab(mContext, R.mipmap.icon_navigation_find, "视频");
        selfTab = new BottomBarTab(mContext, R.mipmap.icon_navigation_self, "个人");
        mBottomBar
                .addItem(homeTab)
                .addItem(categoryTab)
                .addItem(cartTab)
                .addItem(findTab)
                .addItem(selfTab);
        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

    }


}
