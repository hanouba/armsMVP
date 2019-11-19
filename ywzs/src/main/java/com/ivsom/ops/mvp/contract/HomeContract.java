package com.ivsom.ops.mvp.contract;

import android.app.Activity;

import com.ivsom.ops.mvp.model.entity.BaseResponse;
import com.ivsom.ops.mvp.model.entity.HomeDataBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Map;

import io.reactivex.Observable;

/**
 * @author HanN on 2019/11/11 14:04
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description:  首页
 * @updateuser:
 * @updatedata: 2019/11/11 14:04
 * @updateremark:
 * @version: 2.1.67
 */
public interface HomeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        Activity getActivity();
        //申请权限
        RxPermissions getRxPermissions();

    }
    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model extends IModel {
        //获取部分数据
        Observable<BaseResponse<HomeDataBean>> getHomeChartData(Map map);
    }
}
