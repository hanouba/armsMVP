package com.hansen.socket;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hansen.fullvideo.MainActivity;
import com.hansen.fullvideo.R;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketActivity extends AppCompatActivity implements View.OnClickListener {
    private Button connect,sendMsg,clean;
    private EditText edPort,edIP,edData;
    private TextView txReceive;
    private final MyHandler myHandler = new MyHandler(this);
    private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();


    public static Context context ;
    private static TcpClient tcpClient = null;

    ExecutorService exec = Executors.newCachedThreadPool();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        context = this;
        initView();
        IntentFilter intentFilter = new IntentFilter("tcpClientReceiver");
        registerReceiver(myBroadcastReceiver,intentFilter);
    }


    private void initView() {
        connect = findViewById(R.id.connect);
        sendMsg = findViewById(R.id.sendmsg);
        clean = findViewById(R.id.clean);
        edIP = findViewById(R.id.et_ip);
        edPort = findViewById(R.id.et_port);
        edData = findViewById(R.id.et_sendmsg);
        txReceive = findViewById(R.id.receive);

        connect.setOnClickListener(this);
        sendMsg.setOnClickListener(this);
        clean.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connect:
            tcpClient = new TcpClient(edIP.getText().toString(),6000) ;
                exec.execute(tcpClient);
                break;
            case R.id.sendmsg:
                Message message = Message.obtain();
                message.what = 2;
                message.obj = sendMsg.getText().toString();
                myHandler.sendMessage(message);
                exec.execute(new Runnable() {
                    @Override
                    public void run() {
                        tcpClient.send(txReceive.getText().toString());
                    }
                });

                break;
            case R.id.clean:
                    tcpClient.closeSelf();
                break;
                default:
        }
    }

    private class MyHandler extends android.os.Handler{
        private WeakReference<SocketActivity> mActivity;

        MyHandler(SocketActivity activity){
            mActivity = new WeakReference<SocketActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity != null){
                switch (msg.what){
                    case 1:
                        txReceive.append(msg.obj.toString());
                        break;
                    case 2:
                        sendMsg.append(msg.obj.toString());
                        break;
                }
            }
        }
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String mAction = intent.getAction();
            switch (mAction){
                case "tcpClientReceiver":
                    String msg = intent.getStringExtra("tcpClientReceiver");
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = msg;
                    myHandler.sendMessage(message);
                    break;
            }
        }
    }







    @Override
    public void onStop() {
        unregisterReceiver(myBroadcastReceiver);
        super.onStop();
    }
}
