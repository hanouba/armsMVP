package com.ivsom.ops.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivsom.ops.R;
import com.ivsom.ops.app.base.BaseSupportFragment;
import com.jess.arms.di.component.AppComponent;


/**
 * @author HanN on 2019/11/6 15:34
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description:
 * @updateuser:
 * @updatedata: 2019/11/6 15:34
 * @updateremark:
 * @version: 2.1.67
 */
public class DeviceFragment extends BaseSupportFragment {
    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_device,container,false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void post(Runnable runnable) {

    }
}
