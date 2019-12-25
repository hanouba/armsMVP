package com.hansen.fullvideo;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.hansen.fullvideo.bean.BigScreenBean;
import com.hansen.fullvideo.bean.TemplateBean;
import com.hansen.fullvideo.dao.DBHelper;
import com.hansen.fullvideo.floatactionbutton.FloatingActionButton;
import com.hansen.fullvideo.ui.CommonDialog;
import com.hansen.fullvideo.utils.LogUtils;
import com.hansen.fullvideo.utils.SPUtils;
import com.hansen.fullvideo.utils.Utils;
import com.hansen.socket.TcpClient;

import org.greenrobot.greendao.annotation.ToMany;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etIp, etPort;
    private Button login;
    private CheckBox cbSave;
    private CommonDialog mDialog;
    public static TcpClient tcpClient = null;
    ExecutorService exec = Executors.newCachedThreadPool();

    private final MyHandler myHandler = new MyHandler(this);
    private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    private boolean connect = true;
    private boolean saveState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        IntentFilter intentFilter = new IntentFilter("tcpClientReceiver");
        registerReceiver(myBroadcastReceiver, intentFilter);

        initView();
        mDialog = new CommonDialog(this);


    }

    private void initView() {
        etIp = findViewById(R.id.et_ip);
        etPort = findViewById(R.id.et_port);
        login = findViewById(R.id.bt_login);
        cbSave = findViewById(R.id.cb_save);

        login.setOnClickListener(this);
        SPUtils.getInstance().put("SAVE_STATE",true);



            String ip = SPUtils.getInstance().getString("IP");
            String port = SPUtils.getInstance().getString("PORT");
            etIp.setText(ip);
            etPort.setText(port);



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                String ip = etIp.getText().toString();
                String port = etPort.getText().toString();

                if (  checkIp(ip,port)) {

                        SPUtils.getInstance().put("IP",ip);
                        SPUtils.getInstance().put("PORT",port);

                    initTCP(ip, Integer.parseInt(port));
                            openGreen();


                }else {
                    Toast.makeText(this,"ip或者端口错误", Toast.LENGTH_LONG).show();
                }






                break;
        }
    }

    private boolean checkIp(String ip, String port) {
        if (ip.isEmpty() || port.isEmpty()) {
        return false;
        }

        boolean b1 = ip.matches("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");

        return b1;

    }

    public void openGreen() {
        connect = true;
        Intent intent = new Intent(this,GreenActivity.class);
        startActivity(intent);
        finish();
    }

    private void initTCP(String ip, int port) {
        tcpClient = new TcpClient(this, ip, port);
        exec.execute(tcpClient);
    }


    private class MyHandler extends android.os.Handler {
        private WeakReference<LoginActivity> mActivity;

        MyHandler(LoginActivity activity) {
            mActivity = new WeakReference<LoginActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity != null) {
                switch (msg.what) {
                    case 1:
                        connect = false;
                        mDialog.setMessage("服务连接失败,请检查")
                                .setTitle("提示")
                                .setEditorText(false)
                                .setSingle(true).setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                            @Override
                            public void onPositiveClick(String name) {
                                mDialog.dismiss();

                            }

                            @Override
                            public void onNegtiveClick() {
                                mDialog.dismiss();

                            }
                        }).show();
                        break;

                }
            }
        }
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String mAction = intent.getAction();
            switch (mAction) {
                case "tcpClientReceiver":
                    LogUtils.d("TcpClient","tcpClientReceiver");
                    Message message = Message.obtain();
                    message.what = 1;
                    myHandler.sendMessage(message);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }
}
