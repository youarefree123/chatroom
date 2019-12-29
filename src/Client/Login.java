package Client;

/**
 * 登陆界面模块
 */

import java.awt.*;

import javax.swing.*;

public class Login {
    private JTextField textField = null; //用户名文本域
    private JPasswordField pwdField = null; //密码文本域
    Client.LoginListen loginListener=null; //登录按钮监听
    Client.RegisterListen registerListen = null; //注册按钮监听
    // 构造函数
    public Login() {
        init();
    }

    void init() {
        JFrame frame = new JFrame("登录"); //框架
        frame.setBounds(600, 300, 310, 210); //设置位置大小
        frame.setResizable(false);  // 设置不可改变大小
        JPanel mainPanel = new JPanel(new BorderLayout());  // BorderLayout边框布局

        JPanel panel1 = new JPanel(); //面板1
        JLabel headJLabel = new JLabel("登录界面"); //登陆标签
        headJLabel.setFont(new Font(null, 0, 35));  // 设置文本的字体类型、样式 和 大小
        panel1.add(headJLabel);

        JPanel panel2 = new JPanel(); //面板2
        JLabel nameJLabel = new JLabel("用户名："); //用户名标签
        textField = new JTextField(20);
        JLabel pwdJLabel = new JLabel("密码：    "); //密码标签
        pwdField = new JPasswordField(20);
        JButton loginButton = new JButton("登录"); //登录按钮
        JButton registerButton = new JButton("注册");  // 注册按钮
        panel2.add(nameJLabel);
        panel2.add(textField);
        panel2.add(pwdJLabel);
        panel2.add(pwdField);
        panel2.add(loginButton);
        panel2.add(registerButton);
        mainPanel.add(panel1, BorderLayout.NORTH);
        mainPanel.add(panel2, BorderLayout.CENTER);

        // 设置监听
        loginListener = new Client().new LoginListen();  // 新建登陆按钮监听类
        loginListener.setJTextField(textField);  // 将用户名传入
        loginListener.setJPasswordField(pwdField); //将密码传入
        loginListener.setJFrame(frame); //将整个框架传入
        pwdField.addActionListener(loginListener); //文本域监听，敲击回车触发事件
        loginButton.addActionListener(loginListener);  // 登陆按钮添加监听

        registerListen = new Client().new RegisterListen(); // 新建注册按钮监听类
        registerButton.addActionListener(registerListen); //注册按钮添加监听
        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 设置关闭图标作用
        frame.setVisible(true);  // 设置可见
    }
}


