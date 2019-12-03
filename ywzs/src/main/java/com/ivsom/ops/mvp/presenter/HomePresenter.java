package com.ivsom.ops.mvp.presenter;

import android.app.Application;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ivsom.ops.app.AppCounts;
import com.ivsom.ops.mvp.contract.HomeContract;
import com.ivsom.ops.mvp.model.entity.BaseResponse;
import com.ivsom.ops.mvp.model.entity.HomeDataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * @author HanN on 2019/11/11 14:07
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description:
 * @updateuser:
 * @updatedata: 2019/11/11 14:07
 * @updateremark:
 * @version: 2.1.67
 */
@ActivityScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;


    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
    }

    public void getHomeChartData() {
        String sid = SPUtils.getInstance().getString(AppCounts.SESSION_ID);
        Map<String, String> map = new HashMap();
        map.put(AppCounts.KEY_CMD, AppCounts.CMD);
        map.put(AppCounts.KEY_SOURCEAPPID, AppCounts.SOURCEAPPID);
        map.put(AppCounts.KEY_ASLP, "aslp://com.actionsoft.apps.ivsom/GetHomeChartData");
        map.put(AppCounts.KEY_AUTHENTICATION, sid);
        mModel.getHomeChartData(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .doOnSubscribe(disposable -> {
                    //请求时
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    //请求完成
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<HomeDataBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<HomeDataBean> homeDataBeanBaseResponse) {
                        ToastUtils.showShort("首页数据",homeDataBeanBaseResponse.getResult());
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }
}
