package me.jessyan.mvparms.demo.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;

import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.app.base.BaseSupportFragment;

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
public class HomeFragment extends BaseSupportFragment {

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return  inflater.inflate(R.layout.fragment_home,container,false);
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
