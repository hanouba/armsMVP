package com.hansen.videoview.contant;

import android.content.Context;
import android.content.SharedPreferences;


import com.hansen.videoview.R;

/**
 * @author HanN on 2019/12/12 15:22
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description: 通过sp 获取之前设置界面设置信息
 * @updateuser:
 * @updatedata: 2019/12/12 15:22
 * @updateremark:
 * @version: 2.1.67
 */
public class Settings {
    private Context mAppContext;
    private SharedPreferences mSharedPreferences;
//    自动播放
    public static final int PV_PLAYER__AUTO = 0;
    //使用原生mediaplayer
    public static final int PV_PLAYER__ANDROIDMEDIAPLAYER = 1;
    public static final int PV_PLAYER__IJKMEDIAPLAYER = 2;
    public static final int PV_PLAYER__IJKEXOMEDIAPLAYER = 3;

    /**
     * 初始化
     * @param context
     */
    public Settings(Context context) {
        mAppContext = context.getApplicationContext();
        mSharedPreferences = context.getSharedPreferences("ijkplayerdata",Context.MODE_PRIVATE);
    }

    /**
     * 是否可以后台播放
     * @return
     */
    public boolean getEnableBackgroundPlay() {
        String key = mAppContext.getString(R.string.pref_key_enable_background_play);
        return mSharedPreferences.getBoolean(key, false);
    }

    /**
     *
     * @return 默认返回0
     */
    public int getPlayer() {
        String key = mAppContext.getString(R.string.pref_key_player);
        String value = mSharedPreferences.getString(key, "");
        try {
            return Integer.valueOf(value).intValue();
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public boolean getUsingMediaCodec() {
        String key = mAppContext.getString(R.string.pref_key_using_media_codec);
        return mSharedPreferences.getBoolean(key, false);
    }

    /**
     * 通过媒体编解码器自动旋转;
     * @return
     */
    public boolean getUsingMediaCodecAutoRotate() {
        String key = mAppContext.getString(R.string.pref_key_using_media_codec_auto_rotate);
        return mSharedPreferences.getBoolean(key, false);
    }

    /**
     * 让媒体编解码器处理分辨率改变;
     * @return
     */
    public boolean getMediaCodecHandleResolutionChange() {
        String key = mAppContext.getString(R.string.pref_key_media_codec_handle_resolution_change);
        return mSharedPreferences.getBoolean(key, false);
    }

    /**
     * 获得使用开放Sles;
     * @return
     */
    public boolean getUsingOpenSles() {
        String key = mAppContext.getString(R.string.pref_key_using_opensl_es);
        return mSharedPreferences.getBoolean(key, false);
    }

    /**
     * 得到的像素格式
     * @return
     */
    public String getPixelFormat() {
        String key = mAppContext.getString(R.string.pref_key_pixel_format);
        return mSharedPreferences.getString(key, "");
    }

    /**
     * 是否没有视图
     * @return
     */
    public boolean getEnableNoView() {
        String key = mAppContext.getString(R.string.pref_key_enable_no_view);
        return mSharedPreferences.getBoolean(key, false);
    }

    /**
     * 是否使用SurfaceView
     * @return
     */
    public boolean getEnableSurfaceView() {
        String key = mAppContext.getString(R.string.pref_key_enable_surface_view);
        return mSharedPreferences.getBoolean(key, false);
    }

    /**
     * 是否使用TextureView
     * @return
     */
    public boolean getEnableTextureView() {
        String key = mAppContext.getString(R.string.pref_key_enable_texture_view);
        return mSharedPreferences.getBoolean(key, false);
    }

    /**
     * 是否使用分离表面纹理视图;
     * @return
     */
    public boolean getEnableDetachedSurfaceTextureView() {
        String key = mAppContext.getString(R.string.pref_key_enable_detached_surface_texture);
        return mSharedPreferences.getBoolean(key, false);
    }

    /**
     * 使用媒体的数据来源;
     * @return
     */
    public boolean getUsingMediaDataSource() {
        String key = mAppContext.getString(R.string.pref_key_using_mediadatasource);
        return mSharedPreferences.getBoolean(key, false);
    }

    /**
     *
     * @return
     */
    public String getLastDirectory() {
        String key = mAppContext.getString(R.string.pref_key_last_directory);
        return mSharedPreferences.getString(key, "/");
    }

    public void setLastDirectory(String path) {
        String key = mAppContext.getString(R.string.pref_key_last_directory);
        mSharedPreferences.edit().putString(key, path).apply();
    }
}
