package com.hansen.socket;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hansen.fullvideo.GreenActivity;
import com.hansen.fullvideo.ui.CommonDialog;
import com.hansen.fullvideo.utils.LogUtils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author HanN on 2019/12/23 16:36
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description:
 * @updateuser:
 * @updatedata: 2019/12/23 16:36
 * @updateremark:
 * @version: 2.1.67
 */
public  class TcpClient implements Runnable {
    private String TAG = "TcpClient";
//    private String  serverIP = "53.2.1.145";
    private String  serverIP = "172.30.117.1";
    private int serverPort = 1024;
    private PrintWriter pw;
    private Socket socket = null;
    private Context context;

    public TcpClient(Context context,String serverIP, int serverPort) {
        this.context = context;
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    public void closeSelf(){

        try {
            if (pw !=null && socket !=null) {
                pw.close();
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String msg){
        try {
            LogUtils.d(TAG,"发送");
            InputStream inputstr = socket.getInputStream();
            pw.println(msg);
            pw.flush();
        }catch (Exception e) {
            LogUtils.d(TAG,"发送失败");
            Intent intent =new Intent();
            intent.setAction("tcpClientSendFaild");
            GreenActivity.context.sendBroadcast(intent);
        }

    }

    @Override
    public void run() {
        try {
            socket = new Socket(serverIP,serverPort);
            socket.setSoTimeout(3000);
            pw = new PrintWriter(socket.getOutputStream(),true);
            LogUtils.d(TAG,"链接");

        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.d(TAG,"链接失败");
            Intent intent =new Intent();
            intent.setAction("tcpClientReceiver");
            context.sendBroadcast(intent);
        }


    }


}
