package me.jessyan.mvparms.demo.app.service;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.jess.arms.base.BaseService;
import com.jess.arms.utils.LogUtils;

/**
 * @author HanN on 2019/11/5 13:57
 * @email: 1356548475@qq.com
 * @project mvparms
 * @description: 通过messager实现进程间通信
 * @updateuser:
 * @updatedata: 2019/11/5 13:57
 * @updateremark:
 * @version: 2.1.67
 */
public class MessengerService extends BaseService {
    public static final int MSG_SAY_HELLO = 1;
    private static final String TAG = "MessengerService";

    @Override
    public void init() {

    }


    final Messenger messenger = new Messenger(new LocalHandler());
    /**
     * 第一步 创建handler
     * 通过handler创建messenger对象
     */
    private class LocalHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    //获取messenger对象
                    Messenger messenger = msg.replyTo;
                    Message replyMsg =  Message.obtain(null,MessengerService.MSG_SAY_HELLO,0,0);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply","ok~,I had receiver message from you");
                    replyMsg.setData(bundle);
                    LogUtils.debugInfo(TAG,"reply");
                    try {
                        messenger.send(replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);

            }
        }
    }





    /**
     * 当绑定service 时 返回一个通过messenger返回的一个实现Ibinder接口的实例对象
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.debugInfo(TAG,"onBind");
        return messenger.getBinder();
    }
}
