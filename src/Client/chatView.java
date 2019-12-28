package Client;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 聊天室界面模块
 */
public class chatView {
    String userName;  //由客户端登录时设置
    Client.ChatViewListen listener; //聊天界面监听

    // 构造方法
    public chatView(String userName) {
        this.userName = userName ;
        init();
    }

    // 初始化
    void init() {
        JFrame frame = new JFrame("客户端");
        frame.setBounds(500,200,400,330);  //设置坐标和大小
        frame.setResizable(false);  // 缩放为不能缩放

        JPanel panel1 = new JPanel();
        JLabel lable = new JLabel("用户：" + userName);
        //聊天文本域
        JTextArea textArea = new JTextArea("************用户："+userName+" 登录成功，这里你可以畅所欲言！***********\n",12, 35);
        textArea.setEditable(false);  // 设置为不可修改
        JScrollPane scroll = new JScrollPane(textArea);  // 设置滚动面板（装入textArea）
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  // 显示垂直条
        panel1.add(lable);
        panel1.add(scroll);

        JTextField text = new JTextField(20);//发送文本区
        JButton but = new JButton("发送");
        JButton openFileBut = new JButton("传输文件");
        panel1.add(text);
        panel1.add(but);
        panel1.add(openFileBut);

        // 设置“打开文件”监听
        openFileBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                showFileOpenDialog(frame);
                // 创建一个默认的文件选择器
                JFileChooser fileChooser = new JFileChooser();
                // 设置默认显示的文件夹
                fileChooser.setCurrentDirectory(new File("C:/Users/admin/Desktop"));
                // 设置默认使用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
                fileChooser.setFileFilter(new FileNameExtensionFilter("(txt)", "txt"));
                int result = fileChooser.showOpenDialog(frame);  // 对话框将会尽量显示在靠近 parent 的中心
                // 点击确定
                if(result == JFileChooser.APPROVE_OPTION) {
                    // 获取路径
                    File file = fileChooser.getSelectedFile(); //获取文件对象
                    String path = file.getAbsolutePath(); //获取文件绝对路径
                    ClientFileThread.outFileToServer(path); //传输文件线程
                }
            }});

        // 设置“发送”监听
        listener = new Client().new ChatViewListen(); //
        listener.setJTextField(text);  // 调用PoliceListen类的方法
        listener.setJTextArea(textArea);
        listener.setChatViewJf(frame);
        text.addActionListener(listener);  // 文本框添加监听
        but.addActionListener(listener);  // 按钮添加监听

        frame.add(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 设置右上角关闭图标的作用
        frame.setVisible(true);  // 设置可见
    }

}

