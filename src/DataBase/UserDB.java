package DataBase;

import Client.Login;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import sun.rmi.server.UnicastServerRef;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author GUOFENG  --登录连接数据库
 *
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.concurrent.locks.Lock;


/**
 * 数据库工具类
 * @author Administrator
 *
 */
public class UserDB {
    Statement statement; //语句对象
    public UserDB()throws SQLException{
            Connection conn = getConnection();
            this.statement = conn.createStatement(); //可以向数据库发送要执行的SQL语句
    }

    public Statement getStatement() {
        return statement;
    }

    /**
     * 与数据库建立连接
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public static Connection getConnection() throws SQLException{
//        Properties prop = new Properties();
//        try (InputStream in = Files.newInputStream(Paths.get("./DataBase/database.properties"))) {
//            prop.load(in);
//        }
        String drivers = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/chat?useUnicode=true&useSSL=false"; //新版mysql驱动需要加时区
        String username = "root";
        String password = "147258369";
        return DriverManager.getConnection(url, username, password);
    }

//    /**
//     * 遍历表
//     * @param statement
//     * @throws SQLException
//     */
//    public static void showResultSet(Statement statement) throws SQLException
//    {
//        String sql ="select * from chat.user";
//        ResultSet result = statement.executeQuery(sql);
//        ResultSetMetaData metaData = result.getMetaData();
//        int columnCount = metaData.getColumnCount();
//
//        for (int i = 1; i <= columnCount; i++)
//        {
//            if (i > 1) System.out.print(", ");
//            System.out.print(metaData.getColumnLabel(i));
//        }
//        System.out.println();
//
//        while (result.next())
//        {
//            for (int i = 1; i <= columnCount; i++)
//            {
//                if (i > 1) System.out.print(", ");
//                System.out.print(result.getString(i));
//            }
//            System.out.println();
//        }
//    }

    /**
     * 验证用户名和密码:如果正确返回true，否则false
     * @param statement
     * @param name
     * @param password
     * @return
     * @throws SQLException
     */
    public static boolean verify(Statement statement ,String name,String password){
        String sql ="select * from chat.user"; //SQL语句：查询user中所有项
        boolean flag = false;
        try {
           ResultSet result = statement.executeQuery(sql); // 执行语句，返回一个结果集合
           while(result.next()){
               if(result.getString(1).equals(name) && result.getString(2).equals(password)){
                   flag = true;
                   break;
               }
           }
//           flag = false;
       }
       catch (SQLException e){}
       finally {
            return flag;
        }
    }


    /**
     * 查询用户:如果查询到了就返回true,否则返回false
     * @param statement
     * @param name
     */
    public static boolean check(Statement statement, String name){
        boolean flag = false;
        try{
            String sql = "SELECT NAME FROM CHAT.USER WHERE NAME = '"+name+"'";
            ResultSet resultSet = statement.executeQuery (sql);
            while (resultSet.next()){
                if (resultSet.getString(1).equals(name)){
                    flag = true;
                    break;
                }
            }
        }
        catch (Exception e){}
        finally {
            return  flag;
        }
}


    /**
     * 插入数据
     * @param statement
     * @param name
     * @param password
     * @throws SQLException
     */
    public static void update(Statement statement,String name ,String password)
    {
        try{
                String sql = "INSERT INTO CHAT.USER VALUES ('"+name+"','"+password+"')";
                //执行更新操作
                statement.executeUpdate(sql);
//                System.out.println("插入成功");
        }
        catch (Exception e){}
    }




//    public static void main(String[] args)throws SQLException, IOException {
//        Connection conn = getConnection();
//        System.out.println("是否成功连接pg数据库" + conn);
//
//        Statement statement = conn.createStatement(); //可以向数据库发送要执行的SQL语句
//
////        showResultSet(statement); //列出表内数据
////          update(statement,"蒋骏","123456");
////         System.out.println(check(statement,"删除"));
//        System.out.println(verify(statement,"蒋骏","123456"));
//    }
}
