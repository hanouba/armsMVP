package com.hansen.fullvideo;

import android.app.Application;

import com.hansen.fullvideo.utils.Utils;
import com.tencent.bugly.Bugly;


/**
 * @author HanN on 2019/12/17 17:16
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description:
 * @updateuser:
 * @updatedata: 2019/12/17 17:16
 * @updateremark:
 * @version: 2.1.67
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        Bugly.init(getApplicationContext(), "1797b8bc39", true);

    }
}
