package me.jessyan.mvparms.demo.mvp.model.api.service;

import java.util.Map;

import io.reactivex.Observable;
import me.jessyan.mvparms.demo.mvp.model.entity.BaseResponse;
import me.jessyan.mvparms.demo.mvp.model.entity.HomeDataBean;
import me.jessyan.mvparms.demo.mvp.model.entity.LoginBean;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author HanN on 2019/11/13 13:34
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description:
 * @updateuser:
 * @updatedata: 2019/11/13 13:34
 * @updateremark:
 * @version: 2.1.67
 */
public interface HomeService {
    @FormUrlEncoded
    @POST("/portal/r/jd")
    Observable<BaseResponse<HomeDataBean>> getHomeChartData(@FieldMap Map<String,String> map);
}
