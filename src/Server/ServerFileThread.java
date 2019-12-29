package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务端文件传输线程类，监听端口8848，如果有文件传输任务，则开启
 * 文件传输线程。
 * 采用多线程：多个用户可能同时有传送文件的动作
 */

public class ServerFileThread extends Thread{
    ServerSocket server = null;
    Socket socket = null;
    static List<Socket> list = new ArrayList<Socket>();  // 存储客户端

    public void run() {
        try {
            server = new ServerSocket(8848);
            while(true) {
                socket = server.accept(); //监听8848端口
                list.add(socket); //存储客户端
                // 开启文件传输线程
                ServerFileOutput ft = new ServerFileOutput(socket); //开启一个服务器文件传输线程
                ft.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}