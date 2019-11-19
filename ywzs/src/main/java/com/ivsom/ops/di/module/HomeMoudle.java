package com.ivsom.ops.di.module;

import android.support.v4.app.FragmentActivity;

import com.ivsom.ops.mvp.contract.HomeContract;
import com.ivsom.ops.mvp.model.HomeModel;
import com.jess.arms.di.scope.ActivityScope;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * @author HanN on 2019/11/11 17:28
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description:
 * @updateuser:
 * @updatedata: 2019/11/11 17:28
 * @updateremark:
 * @version: 2.1.67
 */
@Module
public abstract class HomeMoudle {
    @Binds
    abstract HomeContract.Model bindHomeModel(HomeModel model);

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(HomeContract.View view) {
        return new RxPermissions((FragmentActivity) view.getActivity());
    }


}
