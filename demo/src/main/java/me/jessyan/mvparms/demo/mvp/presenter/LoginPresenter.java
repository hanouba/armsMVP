package me.jessyan.mvparms.demo.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.operators.flowable.FlowableOnBackpressureDrop;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.mvparms.demo.di.module.LoginModule;
import me.jessyan.mvparms.demo.mvp.contract.LoginContract;
import me.jessyan.mvparms.demo.mvp.contract.UserContract;
import me.jessyan.mvparms.demo.mvp.model.UserModel;
import me.jessyan.mvparms.demo.mvp.model.entity.BaseResponse;
import me.jessyan.mvparms.demo.mvp.model.entity.LoginBean;
import me.jessyan.mvparms.demo.mvp.model.entity.ProjectBean;
import me.jessyan.mvparms.demo.mvp.model.entity.User;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import static me.jessyan.mvparms.demo.app.AppCounts.ASLP_LOGIN;
import static me.jessyan.mvparms.demo.app.AppCounts.CMD;

import static me.jessyan.mvparms.demo.app.AppCounts.LOGIN_AUTHENTICATION;
import static me.jessyan.mvparms.demo.app.AppCounts.SOURCEAPPID;

/**
 * Create by HanN on 2019/10/15
 * 注释:
 */
@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model,LoginContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;




    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
        super(model, rootView);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        LogUtils.d("OnLifecycleEventTAG","onCreate");
    }


    public void getPorject(String project,String userName,String passWord) {
        mModel.checkProject(project)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .doOnSubscribe(disposable -> {
                    //执行过程中显示ui动画
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(()-> {
                    //执行完毕的动画
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<ProjectBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<ProjectBean> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            login(project,userName,passWord);
                        }else if (baseResponse.getCode() == 500) {
                            login(project,userName,passWord);
                        }else {
                            ToastUtils.showShort(baseResponse.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        ToastUtils.showShort(t.getMessage());
                    }
                });
    }

    private void login(String project, String name,String psd) {

        Map<String,String> paramas = new HashMap<>();
        paramas.put("projectName", project);
        paramas.put("account", name);
        paramas.put("password", psd);
        paramas.put("deviceType", "mobile");


        Map<String,String> param = new HashMap<>();
        param.put("cmd", CMD);
        param.put("sourceAppId", SOURCEAPPID);
        param.put("aslp", ASLP_LOGIN);
        param.put("authentication", LOGIN_AUTHENTICATION);
        param.put("params", new Gson().toJson(paramas));

        mModel.login(param)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .doOnSubscribe(disposable -> {
                    //执行过程中显示ui动画
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(()-> {
                    //执行完毕的动画
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<LoginBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<LoginBean> baseResponse) {
                        mRootView.logingReuslt(baseResponse.getData());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
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
