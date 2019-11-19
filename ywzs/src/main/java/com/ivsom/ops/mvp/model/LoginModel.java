package com.ivsom.ops.mvp.model;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.blankj.utilcode.util.LogUtils;
import com.ivsom.ops.mvp.contract.LoginContract;
import com.ivsom.ops.mvp.model.api.service.LoginService;
import com.ivsom.ops.mvp.model.entity.BaseResponse;
import com.ivsom.ops.mvp.model.entity.LoginBean;
import com.ivsom.ops.mvp.model.entity.ProjectBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Create by HanN on 2019/10/15
 * 注释:
 */
@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.Model {
    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        LogUtils.d("OnLifecycleEventTAG","ON_PAUSE");
    }

    @Override
    public Observable<BaseResponse<ProjectBean>> checkProject(String projectName) {
        return mRepositoryManager.obtainRetrofitService(LoginService.class)
                .checkProject(projectName);
    }

    @Override
    public Observable<BaseResponse<LoginBean>> login(Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(LoginService.class)
                .login(map);
    }


}
