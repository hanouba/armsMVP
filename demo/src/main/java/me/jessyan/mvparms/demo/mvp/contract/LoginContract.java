package me.jessyan.mvparms.demo.mvp.contract;

import android.app.Activity;
import android.print.PrintJob;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import me.jessyan.mvparms.demo.mvp.model.entity.BaseResponse;
import me.jessyan.mvparms.demo.mvp.model.entity.LoginBean;
import me.jessyan.mvparms.demo.mvp.model.entity.ProjectBean;
import me.jessyan.mvparms.demo.mvp.model.entity.User;

/**
 * Create by HanN on 2019/10/15
 * 注释: 登录
 */
public interface LoginContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void startLoadMore();
        void endLoadMore();
        void logingReuslt(LoginBean loginBean);
        Activity getActivity();
        //申请权限
        RxPermissions getRxPermissions();

    }
    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<ProjectBean>> checkProject(String projectName);
        Observable<BaseResponse<LoginBean>> login(Map<String,String> map);
    }
}
