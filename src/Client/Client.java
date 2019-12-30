package Client;
import DataBase.UserDB;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Statement;

public class Client extends Thread {
    static Socket mySocket = null;  // 一定要加上static，否则新建线程时会清空
    static JTextField textInput; //用于输入文本框
    static JTextArea textShow; //用于输出的文本域
    static JFrame chatViewJFrame; //聊天室面板
    private static BufferedReader in = null; //读
    private static PrintWriter out = null; //写
    private static String userName; //用户名

    public static void main(String[] args) { //开启登陆界面
        new Login();
    }

    // 接收从服务端发送来的消息
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));  // 输入字符流
            while (true) {
                String str = in.readLine();  // 获取服务端发送的信息
                textShow.append(str + '\n');  // 添加进聊天客户端的文本区域
                textShow.setCaretPosition(textShow.getDocument().getLength());  // 设置滚动条始终在最下面
            }
        } catch (Exception e) {}
    }

    /**
     * 完成注册监听类
     */
    class RegisteredListen implements ActionListener{
        JTextField textField; //用户名域
        JPasswordField pwdField1; //密码域
        JPasswordField pwdField2; //确认密码域
        JFrame RegisterFrame;  // 注册窗口
        Statement statement; //SQL语句操作对象

        public void setJTextField(JTextField textField) { //得到用户名
            this.textField = textField;
        }
        public void setJPasswordField1(JPasswordField pwdField1) { //密码
            this.pwdField1 = pwdField1;
        }
        public void setJPasswordField2(JPasswordField pwdField2) { //确认密码
            this.pwdField2 = pwdField2;
        }
        public void setJFrame(JFrame jFrame) {  //得到框架
            this.RegisterFrame = jFrame;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            userName = textField.getText(); //得到用户名
            String userPwd1 = String.valueOf(pwdField1.getPassword());  // getPassword方法获得char数组，再转换成String
            String userPwd2 = String.valueOf(pwdField2.getPassword());  // getPassword方法获得char数组，再转换成String
            try {
                statement = new UserDB().getStatement(); //得到statement对象
                }
            catch (Exception exception){
                JOptionPane.showMessageDialog(RegisterFrame, exception.toString(), "提示", JOptionPane.WARNING_MESSAGE);
                exception.printStackTrace();
            }

            if(UserDB.check(statement,userName)){ //如果用户名已经注册了
                JOptionPane.showMessageDialog(RegisterFrame, "该用户名已经被注册，请重新输入！", "提示", JOptionPane.WARNING_MESSAGE);
            }
            else if (!userPwd1.equals(userPwd2)){ //如果两次密码输入不一致
                JOptionPane.showMessageDialog(RegisterFrame, "两次输入密码不一致，请重新输入！",
                        "提示", JOptionPane.WARNING_MESSAGE);
            }
            else {
                UserDB.update(statement,userName,userPwd1); //添加该用户
                JOptionPane.showMessageDialog(RegisterFrame, "恭喜你！注册成功", "注册成功", JOptionPane.WARNING_MESSAGE);
                RegisterFrame.setVisible(false);
            }
        }
    }
    /**
     * 登陆监听类
     */
    class LoginListen implements ActionListener {
        JTextField textField;
        JPasswordField pwdField;
        JFrame loginJFrame;  // 登录窗口本身
        chatView chatView = null;
        Statement statement; //SQL语句操作对象

        public void setJTextField(JTextField textField) {  //获得发送文本域
            this.textField = textField;
        }
        public void setJPasswordField(JPasswordField pwdField) { //获得公共文本域
            this.pwdField = pwdField;
        }
        public void setJFrame(JFrame jFrame) { //获得框架
            this.loginJFrame = jFrame;
        }

        public void actionPerformed(ActionEvent event) {
            userName = textField.getText(); //得到用户名
            String userPwd = String.valueOf(pwdField.getPassword());  // getPassword方法获得char数组，再转换成String
            try {
                statement = new UserDB().getStatement(); //得到statement对象
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(UserDB.verify(statement,userName,userPwd)) {  // 如果用户名密码在user表中
                chatView = new chatView(userName);  // 新建聊天窗口,设置聊天窗口的用户名（静态）
                // 建立和服务器的联系
                try {
                    InetAddress addr = InetAddress.getByName(null);  // 获取主机地址
                    mySocket = new Socket(addr,9700);  // 客户端套接字
                    loginJFrame.setVisible(false);  // 隐藏登录窗口
                    out = new PrintWriter(mySocket.getOutputStream());  // 输出流
                    out.println("用户【" + userName + "】进入聊天室！");  // 发送用户名给服务器
                    out.flush();  // 清空缓冲区out中的数据
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 新建普通读写线程并启动
                start();
                // 新建文件读写线程并启动
                    ClientFileThread fileThread = new ClientFileThread(userName, chatViewJFrame, out);
                    fileThread.start();
            }
            else if(!UserDB.check(statement,userName)){ //如果没有找到用户名
                JOptionPane.showMessageDialog(loginJFrame, "用户名:"+userName+"未被注册!", "提示", JOptionPane.WARNING_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(loginJFrame, "账号或密码错误，请重新输入！", "提示", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * 聊天界面监听类
     */
    class ChatViewListen implements ActionListener{
        public void setJTextField(JTextField text) {
                textInput = text;  // 得到发送域
            }
            public void setJTextArea(JTextArea textArea) {
                textShow = textArea;  // 放在外部类，因为其它地方也要用到
            }
            public void setChatViewJf(JFrame jFrame) {
                chatViewJFrame = jFrame;  // 放在外部类，因为其它地方也要用到
                // 设置关闭聊天界面的监听
                chatViewJFrame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        out.println("用户【" + userName + "】离开聊天室！");
                        out.flush();
                        System.exit(0);
                    }
                });
        }
        // 监听执行函数
        public void actionPerformed(ActionEvent event) {
            try {
                String str = textInput.getText();
                // 文本框内容为空
                if("".equals(str)) {
                    textInput.grabFocus();  // 设置焦点
                    // 弹出消息对话框（警告消息）
                    JOptionPane.showMessageDialog(chatViewJFrame, "输入为空，请重新输入！", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                out.println(userName + "说：" + str);  // 输出给服务端
                out.flush();  // 清空缓冲区out中的数据
                textInput.setText("");  // 清空文本框
                textInput.grabFocus();  // 设置焦点：一开始光标就在发送域
            } catch (Exception e) {}
        }
    }

    /**
     * 注册监听类
     */
    class RegisterListen implements ActionListener{
        RegisterView registerView = null;
        @Override
        public void actionPerformed(ActionEvent e) {
            registerView = new RegisterView();
        }
    }
}
