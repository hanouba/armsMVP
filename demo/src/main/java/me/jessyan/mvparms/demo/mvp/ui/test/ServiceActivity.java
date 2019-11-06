package me.jessyan.mvparms.demo.mvp.ui.test;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.app.service.MessengerService;
import me.jessyan.mvparms.demo.app.service.MyService;
import me.jessyan.mvparms.demo.mvp.ui.activity.MainActivity;

public class ServiceActivity extends BaseActivity {


    @BindView(R.id.bt_make)
    Button btMake;
    @BindView(R.id.bt_bind)
    Button btBind;
    @BindView(R.id.bt_unbind)
    Button btUnbind;
    @BindView(R.id.bt_messenger)
    Button btMessenger;
    @BindView(R.id.bt_send_messenger)
    Button btSendMessenger;


    //判断服务是否已经解绑
    boolean mBound = false;

    //与服务交互的messenger
    private Messenger mService = null;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_service;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


    }



    @OnClick({R.id.bt_make, R.id.bt_bind, R.id.bt_unbind, R.id.bt_messenger, R.id.bt_send_messenger})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_make:
                break;
            case R.id.bt_bind:
                Intent intent = new Intent(ServiceActivity.this, MyService.class);
                bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
                break;
            case R.id.bt_unbind:
                if (mBound) {
                    unbindService(serviceConnection);

                    mBound = false;
                }
                break;
            case R.id.bt_messenger:
                Intent intentt = new Intent(ServiceActivity.this, MessengerService.class);
                bindService(intentt, messengerCon, Service.BIND_AUTO_CREATE);
                mBound = true;
                break;
            case R.id.bt_send_messenger:
                sendMessage(view);
                break;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();

        if (mBound) {
            unbindService(serviceConnection);
            mBound = false;
        }

    }


    /**
     * 客户端通知服务端去发送消息
     * @param view
     */
    private void sendMessage(View view) {
        if (!mBound) return;
        //创建于服务交互的消息实体
        Message obtain = Message.obtain(null,MessengerService.MSG_SAY_HELLO,0,0);
        obtain.replyTo = mReceiveReplyMsg;
        try {
            mService.send(obtain);
            LogUtils.debugInfo("MessengerService","发送消息");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * 跨进程通信
     * 在bindService时 就会获取到mService
     */
    private ServiceConnection messengerCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.debugInfo("MessengerService","onServiceConnected");
            mService = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };


    /**
     * 接收服务器返回的信息
     */
    private Messenger mReceiveReplyMsg = new Messenger(new ReceiverReplayMsgHandler());

    private class ReceiverReplayMsgHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MessengerService.MSG_SAY_HELLO:
                    LogUtils.debugInfo("MessengerService",msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }


    /**
     *bind服务测试
     */
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyLocalBinder binder = (MyService.MyLocalBinder) service;
            LogUtils.debugInfo("demoservice", "onServiceConnected");
            MyService myService = binder.getService();
            myService.myway();
            binder.start();
            mBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.debugInfo("demoservice", "onServiceDisconnected");
            mBound = false;
        }
    };
}
