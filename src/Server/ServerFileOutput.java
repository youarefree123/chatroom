package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ServerFileOutput extends Thread {
    private Socket nowSocket = null; //当前套接字
    private DataInputStream input = null;  //输入流
    private DataOutputStream output = null; //输出流

    //构造函数
    public ServerFileOutput(Socket socket) {
        this.nowSocket = socket;
    }
    public void run() {
        try {
            input = new DataInputStream(nowSocket.getInputStream());  // 得到当前套接字的输入流
            while (true) {
                // 获取文件名字和文件长度
                String textName = input.readUTF();
                long textLength = input.readLong();
                // 发送文件名字和文件长度给所有客户端
                for(Socket socket: ServerFileThread.list) {
                    output = new DataOutputStream(socket.getOutputStream());  // 输出流
                    if(socket != nowSocket) {  // 发送给其它客户端
                        output.writeUTF(textName);
                        output.flush();
                        output.writeLong(textLength);
                        output.flush();
                    }
                }
                // 发送文件内容
                int length = -1;
                long curLength = 0;
                byte[] buff = new byte[1024];
                while ((length = input.read(buff)) > 0) {
                    curLength += length;
                    for(Socket socket: ServerFileThread.list) {
                        output = new DataOutputStream(socket.getOutputStream());  // 输出流
                        if(socket != nowSocket) {  // 发送给其它客户端
                            output.write(buff, 0, length);
                            output.flush();
                        }
                    }
                    if(curLength == textLength) {  // 强制退出
                        break;
                    }
                }
            }
        } catch (Exception e) {
            ServerFileThread.list.remove(nowSocket);  // 线程关闭，移除相应套接字
        }
    }
}