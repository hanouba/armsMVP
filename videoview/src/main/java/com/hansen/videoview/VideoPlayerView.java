package com.hansen.videoview;

import android.content.Context;

import android.content.res.Resources;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hansen.videoview.contant.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * @author HanN on 2019/12/12 15:18
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description: 基于ijkplaye开发的自定义view  基于videoview开发 方法基本都相似 继承不同
 * @updateuser:
 * @updatedata: 2019/12/12 15:18
 * @updateremark:
 * @version: 2.1.67
 */
public class VideoPlayerView extends FrameLayout {


    private Context mAppContext;
    private Settings mSettings;
    //是否可以后台播放
    private boolean mEnableBackgroundPlay;
    //用的哪种类型的view
    public static final int RENDER_NONE = 0;
    public static final int RENDER_SURFACE_VIEW = 1;
    public static final int RENDER_TEXTURE_VIEW = 2;
    //当前渲染方式
    private int mCurrentRender = RENDER_NONE;
    private int mCurrentRenderIndex = 0;

    private int mVideoWidth;
    private int mVideoHeight;
    //播放状态
    private static final int STATE_ERROR = -1;
    //空闲
    private static final int STATE_IDLE = 0;
    //准备中
    private static final int STATE_PREPARING = 1;
    //准备好了
    private static final int STATE_PREPARED = 2;
    //播放中
    private static final int STATE_PLAYING = 3;
    //暂停
    private static final int STATE_PAUSED = 4;
    //播放返回完成
    private static final int STATE_PLAYBACK_COMPLETED = 5;
    //当前状态
    private int mCurrentState = STATE_IDLE;
    private int mTargetState = STATE_IDLE;

    //显示文本
    private TextView subtitleDisplay;
    //视频地址
    private Uri mUri;
    private Map<String, String> mHeaders;
    private int mSeekWhenPrepared;  // recording the seek position while preparing

    private IRenderView.ISurfaceHolder mISurfaceHolder = null;
    private IMediaPlayer mMediaPlayer = null;

    private List<Integer> mAllRenders = new ArrayList<Integer>();
    public VideoPlayerView(Context context) {
        this(context,null);

    }



    public VideoPlayerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public VideoPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVideoView(context);
    }

    private void initVideoView(Context context) {
        mAppContext = context.getApplicationContext();
        mSettings = new Settings(mAppContext);
        initBackground();
        initRenders();
//        初始化视图宽高
        mVideoWidth = 0;
        mVideoHeight = 0;
//        焦点处理
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();

        //添加文字居中
        subtitleDisplay = new TextView(context);
        subtitleDisplay.setTextSize(24);
        subtitleDisplay.setGravity(Gravity.CENTER);
        FrameLayout.LayoutParams layoutParams_txt = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM);
        addView(subtitleDisplay, layoutParams_txt);

        subtitleDisplay.setText("subtitleDisplay");
        subtitleDisplay.setTextColor(getResources().getColor(R.color.color_0B8FFE));
    }

    /**
     * 获取设置界面的设置信息
     * 根据设置信息使用不同的技术渲染
     */
    private void initRenders() {
        //清除设置
        mAllRenders.clear();
        if (mSettings.getEnableSurfaceView()) {
            mAllRenders.add(RENDER_SURFACE_VIEW);
        }

        if (mSettings.getEnableTextureView() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            mAllRenders.add(RENDER_TEXTURE_VIEW);
        }

        if (mSettings.getEnableNoView()) {
            mAllRenders.add(RENDER_NONE);
        }

        if (mAllRenders.isEmpty()) {
            mAllRenders.add(RENDER_SURFACE_VIEW);
        }
        //第一次渲染的类型就从renders中获取第一个参数
        mCurrentRender = mAllRenders.get(mCurrentRenderIndex);
        setRender(mCurrentRender);
    }

    /**
     * 开始渲染
     * @param render
     */
    private void setRender(int render) {
        switch (render) {
            case RENDER_NONE:
                //不需要渲染
                break;
            case RENDER_SURFACE_VIEW:
                //使用surfaceview
                break;
            case RENDER_TEXTURE_VIEW:
                //使用textureview
                break;
                default:
        }
    }

    /**
     *根据是否可以后台播放来处理播放
     */
    private void initBackground() {
        mEnableBackgroundPlay = mSettings.getEnableBackgroundPlay();
        if (mEnableBackgroundPlay) {

        }
    }

    /**
     * 设置资源地址
     * 扩展 支持本地网络资源
     * @param path
     */
    public void setVideoPath(String path) {
        setVideoURI(Uri.parse(path));
    }

    /**
     * 拿到uri地址
     * @param uri
     */
    public void setVideoURI(Uri uri) {
        setVideoURI(uri, null);
    }

    /**
     * 获取视频uri地址 同时设置一些请求头信息
     * 跨域 重定向
     * @param uri
     * @param headers
     */
    public void setVideoURI(Uri uri, Map<String, String> headers) {
        mUri = uri;
        mHeaders = headers;
        mSeekWhenPrepared = 0;
        openVideo();
        requestLayout();
        invalidate();
    }

    /**
     * 打开视频
     */
    private void openVideo() {
        if (mUri == null || mISurfaceHolder == null) {
            return;
        }
        //不会吧标记状态修改
        release(false);

        AudioManager am = (AudioManager) mAppContext.getSystemService(Context.AUDIO_SERVICE);
        am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    }

    /**
     * 释放资源
     * @param release
     */
    private void release(boolean release) {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;

            mCurrentState = STATE_IDLE;
            if (release) {
                mTargetState = STATE_IDLE;
            }
            AudioManager am = (AudioManager) mAppContext.getSystemService(Context.AUDIO_SERVICE);
            am.abandonAudioFocus(null);
        }
    }

}
