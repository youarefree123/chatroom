package Client;


import javax.swing.*;
import java.awt.*;

/**
 * 注册界面模块
 */
public class RegisterView {
    private JTextField textField = null; //用户名文本域
    private JPasswordField pwdField1 = null; //密码文本域
    private JPasswordField pwdField2 = null; //重复密码文本域
    Client.RegisteredListen listener =null; //完成注册按钮监听

    // 构造函数
    public RegisterView() {
        init();
    }

    void init() {
        JFrame frame = new JFrame("注册界面"); //框架
        frame.setBounds(750, 350, 310, 310); //设置位置大小

        JPanel mainPanel = new JPanel(new BorderLayout());  // BorderLayout边框布局
        frame.setResizable(false);  // 设置不可改变大小
        JPanel panel1 = new JPanel(); //面板1
        JLabel headJLabel = new JLabel("请完成注册"); //注册界面标签
        headJLabel.setFont(new Font(null, 0, 35));  // 设置文本的字体类型、样式 和 大小
        panel1.add(headJLabel);

        JPanel panel2 = new JPanel(); //面板2
        JLabel nameJLabel = new JLabel(" 请输入用户名："); //用户名标签
        textField = new JTextField(20);
        panel2.add(nameJLabel);
        panel2.add(textField);

        JLabel pwdJLabel1 = new JLabel("    请输入密码： "); //密码标签
        pwdField1 = new JPasswordField(20);
        panel2.add(pwdJLabel1);
        panel2.add(pwdField1);

        pwdField2 = new JPasswordField(20);
        JLabel pwdJLabel2 = new JLabel("请再次输入密码：  "); //密码标签
        panel2.add(pwdJLabel2);
        panel2.add(pwdField2);

        JButton registerButton = new JButton("完成注册");  // 完成注册按钮
        panel2.add(registerButton);
        mainPanel.add(panel1, BorderLayout.NORTH);
        mainPanel.add(panel2, BorderLayout.CENTER);


        // 设置监听
        listener = new Client().new RegisteredListen();  // 新建注册监听类
        listener.setJTextField(textField);  // 将用户名传入
        listener.setJPasswordField1(pwdField1); //将密码传入
        listener.setJPasswordField2(pwdField2); //将二次确认密码传入
        listener.setJFrame(frame); //将整个框架传入
        pwdField2.addActionListener(listener); //敲击回车出发事件
        registerButton.addActionListener(listener);  // 登陆按钮添加监听


        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 设置关闭图标作用
        frame.setVisible(true);  // 设置可见
    }

    public static void main(String[] args) {
        new RegisterView();
    }
}
