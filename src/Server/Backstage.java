package Server;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 服务器后台框架类
 */
public class Backstage {
    private JTextArea textArea; //显示聊天室信息

    // 用于向文本区域添加信息
    void setTextArea(String str) {
        textArea.append(str+'\n');
        textArea.setCaretPosition(textArea.getDocument().getLength());  // 设置滚动条在最下面
    }


    // 构造函数
    public Backstage() {
        init();
    }

    void init() {
        JFrame frame = new JFrame("服务器端");
        frame.setBounds(500,100,550,580);  // 设置窗口坐标和大小
        frame.setResizable(false);  // 设置为不可缩放

        JPanel mainPanel = new JPanel();  // 新建容器(边框布局)
        JLabel lable = new JLabel("欢迎来到服务器端",SwingConstants.CENTER);
        lable.setFont(new Font(null, 0, 24));  // 设置文本的字体类型、样式 和 大小
        mainPanel.add(lable); //北

        JPanel chatPanel = new JPanel(); //聊天监管面板

        textArea = new JTextArea(25,35); // 新建文本区域并设置长宽
        textArea.setEditable(false);  // 设置为不可修改
        JScrollPane textScroll = new JScrollPane(textArea);  // 设置滚动面板（装入textArea）
        textScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);  // 显示垂直条
        chatPanel.add(textScroll);



        mainPanel.add(chatPanel); //西




//        frame.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//            }
//        });

        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 设置关闭图标作用
        frame.setVisible(true);  // 设置可见
    }
}
