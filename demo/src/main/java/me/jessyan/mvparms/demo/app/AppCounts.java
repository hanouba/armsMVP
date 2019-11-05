package me.jessyan.mvparms.demo.app;

/**
 * Create by HanN on 2019/11/1
 * 注释:
 */
public interface AppCounts {


    //是否是第一次启动
    String FIRST_OPEN = "FIRST_OPEN";
    //网络请求常量
    String SOURCEAPPID = "com.actionsoft.apps.ivsom";
    String CMD = "API_CALL_ASLP";
    //登录
    String ASLP_LOGIN = "aslp://com.actionsoft.apps.ivsom/login";
    String LOGIN_AUTHENTICATION = "HjARJF2xAXcBKh3zijzyF2XknMrrZcKKbUlbrbAZsCZvxJhftiCvUtXUQ0-jG_2aPSxm1gmmlvrOvKage8FRVImxKWHb6SIv8cuCaEg1ZU_Y4csLoCT8MFEwNjdTu1a4onvLGJpNguL0Rb7OUE1cSyhsdhwPv1m2oX64ywJx9e8";


    //登录获取的sid
    String SESSION_ID = "SESSION_ID";
    //登录获取的uid
    String USER_ID = "USER_ID";

}
