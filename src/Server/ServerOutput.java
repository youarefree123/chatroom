package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 服务端发送类，用于接收客户端的消息，并分发给阻塞队列中的其他客户端
 * 采用多线程
 */
public class ServerOutput extends Thread{
    Socket nowSocket = null; //得到的当前客户端进程
    Backstage backstage = null; //得到后台界面
    BufferedReader in =null; //输入流
    PrintWriter out = null; //输出流

    // 构造函数
    public ServerOutput(Socket s, Backstage backstage) {
        this.backstage = backstage;  // 获取多人聊天系统界面
        this.nowSocket = s;  // 获取当前客户端
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(nowSocket.getInputStream()));  // 接收每个发送过信息的客户端的数据
            // 获取客户端信息并把信息发送给所有客户端
            while (true) {
                String str = in.readLine(); //按行读取
                String nowDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());//得到当前时间
                // 发送给所有客户端
                for(Socket socket: Server.list) {
                    out = new PrintWriter(socket.getOutputStream());  // 对每个客户端新建相应的socket套接字
                    out.println(nowDate); //发送时间
                    if(socket == nowSocket) {  // 如果是当前客户端
                        out.println("(你)" + str); //前缀加上(你)
                    }
                    else {  // 发送给其它客户端不用加这个前缀
                        out.println(str);
                    }
                    out.flush();  // 刷新输出流
                }
                // 调用setTextArea方法将信息显示到图形界面
                backstage.setTextArea(str);
            }
        } catch (IOException e) {
            Server.list.remove(nowSocket);  // 线程关闭，移除相应套接字
        }
    }
}
