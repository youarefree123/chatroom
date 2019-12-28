package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
                ServerFileOutput ft = new ServerFileOutput(socket);
                ft.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}