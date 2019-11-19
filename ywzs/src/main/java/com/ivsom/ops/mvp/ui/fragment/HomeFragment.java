package com.ivsom.ops.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivsom.ops.R;
import com.ivsom.ops.app.base.BaseSupportFragment;
import com.ivsom.ops.di.component.DaggerHomeComponent;
import com.ivsom.ops.mvp.contract.HomeContract;
import com.ivsom.ops.mvp.presenter.HomePresenter;
import com.jess.arms.di.component.AppComponent;
import com.tbruyelle.rxpermissions2.RxPermissions;


/**
 * @author HanN on 2019/11/6 15:25
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description: 首页
 * @updateuser:
 * @updatedata: 2019/11/6 15:25
 * @updateremark:
 * @version: 2.1.67
 */
public class HomeFragment extends BaseSupportFragment<HomePresenter> implements HomeContract.View {

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return  inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.getHomeChartData();
    }

    @Override
    public void setData(@Nullable Object data) {

    }



    @Override
    public RxPermissions getRxPermissions() {
        return null;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void post(Runnable runnable) {

    }
}
