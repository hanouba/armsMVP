package com.hansen.socket;

import com.hansen.fullvideo.utils.LogUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author HanN on 2019/12/24 9:32
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description:
 * @updateuser:
 * @updatedata: 2019/12/24 9:32
 * @updateremark:
 * @version: 2.1.67
 */
public class Server {
    private String tag = "Server_Socket";
    public void initServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(2000);
        LogUtils.d(tag,"已经连接了服务器");

        LogUtils.d(tag,"服务端信息"+serverSocket.getInetAddress()+"P"+serverSocket.getLocalPort());
        for (;;) {
            Socket client = serverSocket.accept();
            todo(client);
        }

    }

    private void todo(Socket client) {
        ClientHandler clientHandler = new ClientHandler(client);
        clientHandler.start();
    }

    private static class ClientHandler extends Thread{
        private Socket socket;
        private boolean flag = true;
        ClientHandler(Socket socket) {
            this.socket = socket;

        }


        @Override
        public void run() {
            super.run();
            LogUtils.d("新客户端","服务端信息"+socket.getInetAddress()+"P"+socket.getPort());

            try {
                PrintStream socketOutput = new PrintStream(socket.getOutputStream());
                BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()
                ));
                do {
                    String str = socketInput.readLine();
                    if ("bye".equalsIgnoreCase(str)) {
                        flag = false;
                        socketOutput.println("bye");
                    }else {
                        System.out.println(str);
                        socketOutput.println("回送"+str.length());
                    }
                }while (flag);

                socketInput.close();
                socketOutput.close();
            }catch (Exception e) {
                LogUtils.d("","链接断开");
            }finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            LogUtils.d("","客户端退出");
        }


    }
}
