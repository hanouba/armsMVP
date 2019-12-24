package com.hansen.socket;

import android.content.Intent;
import android.graphics.Color;
import android.renderscript.Int4;

import com.hansen.fullvideo.utils.LogUtils;

import org.xml.sax.ErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author HanN on 2019/12/23 17:45
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description:
 * @updateuser:
 * @updatedata: 2019/12/23 17:45
 * @updateremark:
 * @version: 2.1.67
 */
public class Client {
    private String tag = "Client_Socket";
    public void initSocket() {
        Socket socket = new Socket();
        try {
            socket.setSoTimeout(3000);
            socket.connect(new InetSocketAddress(InetAddress.getLocalHost(),2000),3000);
            LogUtils.d(tag,"已经连接了服务器");
            LogUtils.d(tag,"客户端信息"+socket.getLocalAddress()+"P"+socket.getLocalPort());
            LogUtils.d(tag,"服务端信息"+socket.getInetAddress()+"P"+socket.getPort());

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            //发送接收数据
            todo(socket);
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.d(tag,"异常导致关闭");
        }

        try {
            socket.close();
            LogUtils.d(tag,"客户端退出");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    boolean flag = true;
    private void  todo(Socket clent) throws IOException {
//        构建键盘输入流
        InputStream inputStream = System.in;
        BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
        OutputStream outputStream = clent.getOutputStream();
        PrintStream socketPrint = new PrintStream(outputStream);

        InputStream inputstr = clent.getInputStream();
        BufferedReader socketBuffer = new BufferedReader(new InputStreamReader(inputstr));

        do{


        String string = input.readLine();
        socketPrint.println(string);
        String echo = socketBuffer.readLine();
        if ("bye".equalsIgnoreCase(echo)) {
           flag = false;
        }else {
            System.out.println(echo);
        }
        }while (flag) ;

        input.close();
        socketBuffer.close();

    }
}
