package com.hansen.videoview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import android.view.View;



import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * @author HanN on 2019/12/13 14:10
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description: 视频的显示内容信息
 * @updateuser:
 * @updatedata: 2019/12/13 14:10
 * @updateremark:
 * @version: 2.1.67
 */
public class InfoHudViewHolder {
    private TableLayoutBinder mTableLayoutBinder;
    private SparseArray<View> mRowMap = new SparseArray<View>();
    private IMediaPlayer mMediaPlayer;
    private long mLoadCost = 0;
    private long mSeekCost = 0;


    private static final int MSG_UPDATE_HUD = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_HUD:
                    InfoHudViewHolder holder = InfoHudViewHolder.this;
                    IjkMediaPlayer mp = null;
                    if (mMediaPlayer == null) {
                        break;
                    }else {

                    }

                    break;
                    default:
            }
        }
    };


    public InfoHudViewHolder(Context context,TableLayoutBinder tableLayoutBinder) {
        mTableLayoutBinder = tableLayoutBinder;
    }


    public void  setActionc(int nameId) {
        mTableLayoutBinder.appendSection(nameId);
    }

    /**
     * 添加一行文字 显示view 存在map中 没有设置value
     * @param nameId
     */
    public void  appendRow(int nameId) {
        View rowView = mTableLayoutBinder.appendRow2(nameId, null);
        mRowMap.put(nameId,rowView);
    }

    /**
     * 根据sring id 设置值
     * 不知道为啥要这样设置
     * @param id
     * @param value
     */
    public void setRowValue(int id,String value) {
        View rowView = mRowMap.get(id);
        if (rowView == null) {
            rowView = mTableLayoutBinder.appendRow2(id,value);
            mRowMap.put(id,rowView);
        }else {
            mTableLayoutBinder.setValueText(rowView,value);
        }
    }


    public void setmMediaPlayer(IMediaPlayer pm) {
        mMediaPlayer = pm;
        if (mMediaPlayer != null) {
            mHandler.sendEmptyMessageDelayed(MSG_UPDATE_HUD, 500);
        }else {
            mHandler.removeMessages(MSG_UPDATE_HUD);
        }

    }





}
