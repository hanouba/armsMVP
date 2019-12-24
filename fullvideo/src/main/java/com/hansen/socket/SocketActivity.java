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


    public static Context context ;
    private static TcpClient tcpClient = null;
    private int num = 0;
    ExecutorService exec = Executors.newCachedThreadPool();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        context = this;
        initView();

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
            tcpClient = new TcpClient(this) ;
                exec.execute(tcpClient);
                break;
            case R.id.sendmsg:
                num++;
                exec.execute(new Runnable() {
                    @Override
                    public void run() {
                        tcpClient.send("<call,"+num+",0>");
                    }
                });

                break;
            case R.id.clean:
                    tcpClient.closeSelf();
                break;
                default:
        }
    }











    @Override
    public void onStop() {

        super.onStop();
    }
}
