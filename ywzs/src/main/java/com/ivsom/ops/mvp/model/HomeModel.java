package com.ivsom.ops.mvp.model;

import com.ivsom.ops.mvp.contract.HomeContract;
import com.ivsom.ops.mvp.model.api.service.HomeService;
import com.ivsom.ops.mvp.model.entity.BaseResponse;
import com.ivsom.ops.mvp.model.entity.HomeDataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author HanN on 2019/11/11 14:06
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description:
 * @updateuser:
 * @updatedata: 2019/11/11 14:06
 * @updateremark:
 * @version: 2.1.67
 */
@ActivityScope
public class HomeModel extends BaseModel implements HomeContract.Model {
    @Inject
    public HomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse<HomeDataBean>> getHomeChartData(Map map) {
        return mRepositoryManager.obtainRetrofitService(HomeService.class)
                .getHomeChartData(map);
    }
}
