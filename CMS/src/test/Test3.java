/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import cms.dao.Classinfo;
import cms.dao.ConnectionManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author zqzess
 */
public class Test3 {
    public static void main(String[] args) {
        Connection con=null;
        ResultSet rs=null;
        String result=null;
        try{
            //建立连接
            String conStr="jdbc:mysql://127.0.0.1:3306/cms?user=root&password=123456";
            con=DriverManager.getConnection(conStr);
            //con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("数据库连接成功");
            }
            //创建查询对象
            String sql="SELECT course_info.time FROM course_info;";
            PreparedStatement stm=con.prepareStatement(sql);
            //stm.setString(1,username);
            //查询
            rs=stm.executeQuery();
            String name="";
            while(rs.next())
            {
                name=name+rs.getString(1)+",";
                //System.err.println("while:"+name);
                               
            }
            System.err.println(name);
            result=name.substring(0,name.length()-1);
            System.err.println(result);
        }catch(SQLException ex)
        {
            ex.printStackTrace();
        }finally
        {//资源释放
            if(rs!=null){
                try{
                    rs.close();
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
            if(con!=null){
                try{
                    con.close();
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
    
}
