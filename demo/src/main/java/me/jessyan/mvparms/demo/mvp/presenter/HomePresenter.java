package me.jessyan.mvparms.demo.mvp.presenter;

import android.app.Application;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.mvparms.demo.app.AppCounts;
import me.jessyan.mvparms.demo.mvp.contract.HomeContract;
import me.jessyan.mvparms.demo.mvp.contract.LoginContract;
import me.jessyan.mvparms.demo.mvp.model.entity.BaseResponse;
import me.jessyan.mvparms.demo.mvp.model.entity.HomeDataBean;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import static me.jessyan.mvparms.demo.app.AppCounts.KEY_ASLP;
import static me.jessyan.mvparms.demo.app.AppCounts.KEY_AUTHENTICATION;
import static me.jessyan.mvparms.demo.app.AppCounts.KEY_SOURCEAPPID;
import static me.jessyan.mvparms.demo.app.AppCounts.SOURCEAPPID;

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
public class HomePresenter extends BasePresenter<HomeContract.Model,HomeContract.View> {
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
        map.put(KEY_SOURCEAPPID, SOURCEAPPID);
        map.put(KEY_ASLP, "aslp://com.actionsoft.apps.ivsom/GetHomeChartData");
        map.put(KEY_AUTHENTICATION, sid);
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
                        ToastUtils.showShort("h",homeDataBeanBaseResponse.getResult());
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
