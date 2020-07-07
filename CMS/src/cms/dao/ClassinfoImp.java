/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author zqzess
 */
public class ClassinfoImp implements ClassinfoDao 
{
    public Classinfo[] queryAllClass()
    {//查询所有班级
        ArrayList <Classinfo>ret=new ArrayList<>();
        //User ret=null;
        //String conStr="jdbc:mysql://127.0.0.1:3306/cms?user=root&password=123456";
        Connection con=null;
        ResultSet rs=null;
        try{
            //建立连接
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("查询所有班级-数据库连接成功");
            }
            //创建查询对象
            String sql="select * from class_info";
            PreparedStatement stm=con.prepareStatement(sql);
            //stm.setString(1,username);
            //查询
            rs=stm.executeQuery();
            while(rs.next())
            {
                String name=rs.getString("name");
                
                ret.add(new Classinfo(name));
            }      
            
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
        return ret.toArray(new Classinfo[]{});
    }
    
    public Classinfo[] queryClass(String cid)
    {
        ArrayList <Classinfo>ret=new ArrayList<>();
        Connection con=null;
        ResultSet rs=null;
        try{
            //建立连接
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("查询有某必修课的班级-数据库连接成功!");
                System.out.println(cid);
            }
            //创建查询对象
            String sql="SELECT classname FROM class_course WHERE course_id=?;";
            PreparedStatement stm=con.prepareStatement(sql);
            stm.setString(1, cid);
            //stm.setString(1,username);
            //查询
            rs=stm.executeQuery();
            while(rs.next())
            {
                String name=rs.getString("classname");
                System.out.println(name);
                ret.add(new Classinfo(name));
                
            }      
            
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
        
        return ret.toArray(new Classinfo[]{});
    }
    
    public void teacherDeleteCourse(String cid)
    {
        Connection con=null;
        ResultSet rs=null;
        try{
            //建立连接
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("教师删除课程-数据库连接成功!");
                System.out.println(cid);
            }
            //创建查询对象
            String sql="delete from course_info where id=?;";
            PreparedStatement stm=con.prepareStatement(sql);
            stm.setString(1, cid);
            //stm.setString(1,username);
            //查询
            stm.execute();
            
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
    
    public void createClass(String classname)
    {//插入班级
        Connection con=null;
        ResultSet rs=null;
        try{
            //建立连接
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("创建班级-数据库连接成功!");
                System.out.println(classname);
            }
            //创建查询对象
            String sql="insert into class_info values(?);";
            PreparedStatement stm=con.prepareStatement(sql);
            stm.setString(1, classname);
            //stm.setString(1,username);
            //查询
            stm.execute();
            
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
    
    public Classinfo[] queryClassSearch(String searchid)
    {//查询所有班级
        ArrayList <Classinfo>ret=new ArrayList<>();
        //User ret=null;
        //String conStr="jdbc:mysql://127.0.0.1:3306/cms?user=root&password=123456";
        Connection con=null;
        ResultSet rs=null;
        try{
            //建立连接
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("搜索班级-数据库连接成功");
            }
            //创建查询对象
            String sql="select * from class_info WHERE `name` LIKE ? ";
            PreparedStatement stm=con.prepareStatement(sql);
            String tmp="%"+searchid+"%";
            stm.setString(1,tmp);
            //查询
            rs=stm.executeQuery();
            while(rs.next())
            {
                String name=rs.getString("name");
                
                ret.add(new Classinfo(name));
            }      
            
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
        return ret.toArray(new Classinfo[]{});
    }
    
    
    public int selecClassInfoCount(String classname)
    {//查询该班级总人数
        Connection con=null;
        ResultSet rs=null;
        int count = 0;
        try{
            //建立连接
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("查询该班级总人数-数据库连接成功");
            }
            //创建查询对象
            String sql="SELECT COUNT(username) FROM `user` WHERE classname=?";
            PreparedStatement stm=con.prepareStatement(sql);
            stm.setString(1,classname);
            //查询
            rs=stm.executeQuery();
            while(rs.next())
            {
                count=rs.getInt(1);
                
            }}catch(SQLException ex)
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
        return count;
    }
    
    public String selecClassInfoCourse(String classname)
    {//查询该班级必修课
        Connection con=null;
        ResultSet rs=null;
        String course = null;
        String retunCourse="";
        try{
            //建立连接
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("查询该班级必修课-数据库连接成功");
            }
            //创建查询对象
            String sql="SELECT `name` FROM course_info,class_course WHERE class_course.course_id=course_info.id AND classname=?";
            PreparedStatement stm=con.prepareStatement(sql);
            stm.setString(1,classname);
            //查询
            rs=stm.executeQuery();
            while(rs.next())
            {
                course=rs.getString(1);
                retunCourse=retunCourse+course+",";
                System.out.println("1+"+retunCourse);
                
            }
            System.out.println("2+"+retunCourse);
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
        return retunCourse;
    }
    
    public void updateStudentClassname(String oldClassname,String newClassname)
    {//修改学生绑定的班级为新班级名
        Connection con=null;
        ResultSet rs=null;
        try{
            //建立连接
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("修改学生绑定的班级为新班级名-数据库连接成功");
            }
            //创建查询对象
            String sql="UPDATE `user` SET classname=? WHERE classname=?";
            PreparedStatement stm=con.prepareStatement(sql);
            stm.setString(1,newClassname);
            stm.setString(2,oldClassname);
            //查询
            stm.execute();
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
    
    public void updateClassCourse(String oldClassname,String newClassname)
    {//修改班级绑定的必修课记录
        Connection con=null;
        ResultSet rs=null;
        try{
            //建立连接
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("修改班级绑定的必修课记录-数据库连接成功");
            }
            //创建查询对象
            String sql="UPDATE class_course SET classname=? WHERE classname=?";
            PreparedStatement stm=con.prepareStatement(sql);
            stm.setString(1,newClassname);
            stm.setString(2,oldClassname);
            //查询
            stm.execute();
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
    
    public void deleteClassname(String oldClassname)
    {//删除旧班级
        Connection con=null;
        ResultSet rs=null;
        try{
            //建立连接
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("删除旧班级-数据库连接成功");
            }
            //创建查询对象
            String sql="DELETE FROM class_info WHERE `name`=?";
            PreparedStatement stm=con.prepareStatement(sql);
            stm.setString(1,oldClassname);
            //查询
            stm.execute();
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

            
           


    

  