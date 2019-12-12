package com.dinghao.phoenixtest;

import org.apache.phoenix.jdbc.PhoenixDriver;

import javax.security.auth.login.Configuration;
import java.sql.*;

/**
 * @author dinghao
 * @create 2019-12-12 16:26
 * @message
 */
public class PhoenixTest1 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1、定义参数
        String driver = PhoenixDriver.class.getName();
        String url = "jdbc:phoenix:hadoop101,hadoop102,hadoop103:2181";
        //2、加载驱动
        Class.forName(driver);
        //3、建立连接
        Connection connection = DriverManager.getConnection(url);
        //4、预编译sql
        PreparedStatement preparedStatement = connection.prepareStatement("select * from \"test\"");
        //5、查询返回值
        ResultSet resultSet = preparedStatement.executeQuery();
        //6、打印结果
        while(resultSet.next()){
            System.out.println(resultSet.getString(1)+"-"+resultSet.getString(2));
        }
        //7、关闭资源
        connection.close();
    }
}
