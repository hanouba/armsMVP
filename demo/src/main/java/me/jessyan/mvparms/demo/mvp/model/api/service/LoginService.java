package me.jessyan.mvparms.demo.mvp.model.api.service;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import me.jessyan.mvparms.demo.mvp.model.entity.BaseResponse;
import me.jessyan.mvparms.demo.mvp.model.entity.LoginBean;
import me.jessyan.mvparms.demo.mvp.model.entity.ProjectBean;
import me.jessyan.mvparms.demo.mvp.model.entity.User;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Create by HanN on 2019/11/1
 * 注释: 登录 相关
 */
public interface LoginService {


    @POST("/pro/api/project/match")
    Observable<BaseResponse<ProjectBean>> checkProject(@Query("projectName") String projectName);

    @FormUrlEncoded
    @POST("/portal/r/jd")
    Observable<BaseResponse<LoginBean>> login(@FieldMap Map<String,String> map);
}
