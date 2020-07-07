/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author zqzess
 */
public class ConnectionManager {
    static String conStr="jdbc:mysql://127.0.0.1:3306/cms?user=root&password=123456";
    static Connection getConnection;
    static  Connection getConnection()throws SQLException
    {
        return DriverManager.getConnection(conStr);
    }
    
}
