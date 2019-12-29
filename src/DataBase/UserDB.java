package DataBase;
import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;


/**
 * 数据库工具类：用于操作数据库
 * @author Administrator
 *
 */

public class UserDB {
    Statement statement; //语句对象

    public UserDB() throws SQLException{
            Connection conn = getConnection(); //与数据库建立连接
            this.statement = conn.createStatement(); //可以向数据库发送要执行的SQL语句
    }
    //得到statement对象
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
        String drivers = "com.mysql.jdbc.Driver"; //驱动名称
        String url = "jdbc:mysql://localhost:3306/chat?useUnicode=true&useSSL=false"; //关闭ssl连接
        String username = "root";
        String password = "147258369";
        return DriverManager.getConnection(url, username, password);
    }

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
               //遍历所有entry如果用户名和密码都一致返回true
               if(result.getString(1).equals(name) && result.getString(2).equals(password)){
                   flag = true;
                   break;
               }
           }
       }
       catch (SQLException e){}
       finally {
            return flag;
        }
    }

    /**
     * 查询用户是否存在:如果查询到了就返回true,否则返回false
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

}
