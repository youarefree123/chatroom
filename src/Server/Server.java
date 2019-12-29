package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务器类：用于开启服务器后端界面，监听9700端口，开启文本读取线程和文件传输线程
 */
public class Server{
    static ServerSocket server = null; //服务器连接
    static Socket client = null; //客户端连接
    static List<Socket> list = new ArrayList<Socket>();  // 存储客户端对象

    public static void main(String[] args) {
        Backstage backstage = new Backstage();  // 创建聊天室后台系统界面
        try {
            // 在服务器端对客户端开启文件传输的线程
            ServerFileThread sft = new ServerFileThread();
            sft.start();
            server = new ServerSocket(9700);  // 服务器端套接字
            // 等待连接并开启相应线程
            while (true) {
                client = server.accept();  // 等待连接
                list.add(client);  // 添加当前客户端到列表
                // 在服务器端对客户端开启文本读取的线程
                ServerOutput print = new ServerOutput(client,backstage);
                print.start();
            }
        } catch (IOException e1) {
            e1.printStackTrace();  // 出现异常则打印出异常的位置
        }
    }
}

