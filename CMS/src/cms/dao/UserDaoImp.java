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
public class UserDaoImp implements UserDao {

    @Override
    public User queryUser(String username) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        User ret=null;
        /*if(username.equals("1833001"))
        {
            ret =new User("1833001",0,"jack","123456","计网L","123",0);
        }
        else if(username.equals("1833002"))
        {
            ret =new User("1833002",0,"tom","123456","计网L","123",1); 
        }
        */
        //String conStr="jdbc:mysql://127.0.0.1:3306/cms?user=root&password=123456";
        Connection con=null;
        ResultSet rs=null;
        try{
            //建立连接
            //con=DriverManager.getConnection(conStr);
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("查询用户-数据库连接成功");
            }
            //创建查询对象
            String sql="select*from user where username=?";
            PreparedStatement stm=con.prepareStatement(sql);
            stm.setString(1,username);
            //查询
            rs=stm.executeQuery();
            while(rs.next())
            {
                String name=rs.getString("name");
                int type=rs.getInt("type");
                String phone=rs.getString("phone");
                String classname=rs.getString("classname");
                String password=rs.getString("password");
                int state=rs.getInt("state");
                
                ret=new User(username,type,name,phone,classname,password,state);
                
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
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
        return ret;
    }

    @Override
    public boolean createUser(User u) {
        //录入数据库
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        boolean ret=false;
        
        String conStr="jdbc:mysql://127.0.0.1:3306/cms?user=root&password=123456";
        Connection con=null;
        //ResultSet rs=null;
        try{
            //建立连接
            con=DriverManager.getConnection(conStr);
            if(con!=null)
            {
                System.out.println("创建用户-数据库连接成功");
            }
            //创建查询对象
            String sql="insert into user(username,type,name,phone,classname,password,state) values(?,?,?,?,?,?,?)";
            PreparedStatement stm=con.prepareStatement(sql);
            stm.setString(1,u.getUernameString());
            stm.setInt(2, u.getType());
            stm.setString(3, u.getName());
            stm.setString(4, u.getPhone());
            stm.setString(5, u.getClassname());
            stm.setString(6, u.getPassword());
            stm.setInt(7, u.getState());
            //执行
            //rs=stm.executeQuery();
            stm.execute();
            ret=true;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {//资源释放
            if(con!=null){
                try{
                    con.close();
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
        return ret;
    }
    
//查询所有待审批用户
    public  ArrayList<User>queryWaitingApprovalUsers()
    {
        ArrayList<User>ret=new ArrayList<>();
        Connection con=null;
        ResultSet rs=null;
        try{
            //建立连接
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("查询待审核用户-数据库连接成功");
            }
            //创建查询对象
            //String sql="insert into user(username,type,name,phone,classname,password,state) values(?,?,?,?,?,?,?)";
            String sql="select * from user where state=0";
            PreparedStatement stm=con.prepareStatement(sql);
            rs=stm.executeQuery();
            while(rs.next())
            {
                String nameid=rs.getString("username");
                String namename=rs.getString("name");
                int type=rs.getInt("type");
                String phone=rs.getString("phone");
                String classname=rs.getString("classname");
                String password=rs.getString("password");
                int state=rs.getInt("state");
                
                User u=new User(nameid,type,namename,phone,classname,password,state);
                ret.add(u);
            }
            
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {//资源释放
            if(con!=null){
                try{
                    con.close();
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
        return ret;
    }

    @Override
    public void approvalUser(String userid, boolean action) {
         
        Connection con=null;
        ResultSet rs=null;
        try{
            //建立连接
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("审核用户-数据库连接成功");
            }
            //创建查询对象
            String sql="update user set state=? where username=?";
            PreparedStatement stm=con.prepareStatement(sql);
            if(action)
            {
                stm.setInt(1, 1);
            }else
            {
                stm.setInt(1, 2);
            }
            stm.setString(2, userid);
            //查询
            stm.executeUpdate();
            
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
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
    
    public  ArrayList<User>queryAllUsers()
    {
        ArrayList<User>ret=new ArrayList<>();
        Connection con=null;
        ResultSet rs=null;
        try{
            //建立连接
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("查询所有用户-数据库连接成功");
            }
            //创建查询对象
            //String sql="insert into user(username,type,name,phone,classname,password,state) values(?,?,?,?,?,?,?)";
            //String sql="select * from user;";
            String sql="select * from user WHERE type=0 OR type=1;";
            PreparedStatement stm=con.prepareStatement(sql);
            rs=stm.executeQuery();
            while(rs.next())
            {
                String nameid=rs.getString("username");
                String namename=rs.getString("name");
                int type=rs.getInt("type");
                String phone=rs.getString("phone");
                String classname=rs.getString("classname");
                String password=rs.getString("password");
                int state=rs.getInt("state");
                
                User u=new User(nameid,type,namename,phone,classname,password,state);
                ret.add(u);
            }
            
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {//资源释放
            if(con!=null){
                try{
                    con.close();
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
        return ret;
    }
    
    public boolean AdminupdateUserInfo(User u)
    {//更新用户信息
        boolean ret=false;
        
        //String conStr="jdbc:mysql://127.0.0.1:3306/cms?user=root&password=123456";
        Connection con=null;
        //ResultSet rs=null;
        try{
            //建立连接
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("更新用户信息-数据库连接成功");
            }
            //创建查询对象
            String sql="UPDATE `user` SET type=?,`name`=?,phone=?,classname=?,`password`=?,state=? WHERE username = ?;";
            PreparedStatement stm=con.prepareStatement(sql);
            //stm.setString(1,u.getUernameString());
            stm.setInt(1, u.getType());
            stm.setString(2, u.getName());
            stm.setString(3, u.getPhone());
            stm.setString(4, u.getClassname());
            stm.setString(5, u.getPassword());
            stm.setInt(6, u.getState());
            stm.setString(7,u.getUernameString());
            //执行
            //rs=stm.executeQuery();
            stm.execute();
            ret=true;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {//资源释放
            if(con!=null){
                try{
                    con.close();
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
        return ret;
        
    }
    
    public void AdminDeleteUser(String userid)
    {//管理员删除用户
        Connection con=null;
        ResultSet rs=null;
        try{
            //建立连接
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("删除用户-数据库连接成功!");
                System.out.println(userid);
            }
            //创建查询对象
            String sql="delete from `user` where username=?;";
            PreparedStatement stm=con.prepareStatement(sql);
            stm.setString(1, userid);
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
    
    
    public ArrayList<User>AdminquerySearchType(int searchtype) 
    {
    //查询身份类型
            ArrayList<User>ret=new ArrayList<>();
        //User ret=null;
        Connection con=null;
        ResultSet rs=null;
        try{
            //建立连接
            //con=DriverManager.getConnection(conStr);
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("查询身份类型-数据库连接成功");
            }
            //创建查询对象
            String sql="select*from user where type=?";
            PreparedStatement stm=con.prepareStatement(sql);
            stm.setInt(1,searchtype);
            //查询
            rs=stm.executeQuery();
            while(rs.next())
            {
                String nameid=rs.getString("username");
                String namename=rs.getString("name");
                int type=rs.getInt("type");
                String phone=rs.getString("phone");
                String classname=rs.getString("classname");
                String password=rs.getString("password");
                int state=rs.getInt("state");
                
                User u=new User(nameid,type,namename,phone,classname,password,state);
                ret.add(u);
                
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
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
        return ret;
    }
    
    public ArrayList<User>AdminquerySearcrchEvent(String searchid) 
    {
    //管理员查询
            ArrayList<User>ret=new ArrayList<>();
        //User ret=null;
        Connection con=null;
        ResultSet rs=null;
        try{
            //建立连接
            //con=DriverManager.getConnection(conStr);
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("管理员查询事件-数据库连接成功");
            }
            //创建查询对象
            String sql="SELECT * FROM `user` WHERE `name` LIKE ? AND (type=1 OR type=0) UNION " +
"SELECT * FROM `user` WHERE username LIKE ? AND (type=1 OR type=0) UNION " +
"SELECT * FROM `user` WHERE classname LIKE ? AND (type=1 OR type=0)";
            PreparedStatement stm=con.prepareStatement(sql);
            String tmp="%"+searchid+"%";
            stm.setString(1,tmp);
            stm.setString(2,tmp);
            stm.setString(3,tmp);
            //查询
            rs=stm.executeQuery();
            while(rs.next())
            {
                String nameid=rs.getString("username");
                String namename=rs.getString("name");
                int type=rs.getInt("type");
                String phone=rs.getString("phone");
                String classname=rs.getString("classname");
                String password=rs.getString("password");
                int state=rs.getInt("state");
                
                User u=new User(nameid,type,namename,phone,classname,password,state);
                ret.add(u);
                
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
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
        return ret;
    }
    
    public User ResetPwdVerify(String username) {
        
        User ret=null;
        Connection con=null;
        ResultSet rs=null;
        try{
            //建立连接
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("查询账号是否存在-数据库连接成功");
            }
            //创建查询对象
            String sql="select*from user where username=?";
            PreparedStatement stm=con.prepareStatement(sql);
            stm.setString(1,username);
            //查询
            rs=stm.executeQuery();
            while(rs.next())
            {
                String name=rs.getString("name");
                int type=rs.getInt("type");
                String phone=rs.getString("phone");
                String classname=rs.getString("classname");
                String password=rs.getString("password");
                int state=rs.getInt("state");
                
                ret=new User(username,type,name,phone,classname,password,state);
                
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
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
        return ret;
    }
    
    public void updatePwd(String userid,String pwd)
    {
        Connection con=null;
        ResultSet rs=null;
        boolean ret;
        try{
            //建立连接
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("修改密码-数据库连接成功");
            }
            //创建查询对象
            String sql="UPDATE `user` SET `password`=? WHERE username=?";
            PreparedStatement stm=con.prepareStatement(sql);
            stm.setString(1,pwd);
            stm.setString(2,userid);
            
            //查询
            stm.execute();
            ret=true;
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
