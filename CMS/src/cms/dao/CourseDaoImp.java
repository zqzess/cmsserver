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
public class CourseDaoImp implements CourseDao {

    public CourseInfo[] queryStudentAllCourses(String username) {
        ArrayList<CourseInfo> ret = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            if (con != null) {
                System.out.println("查询学生所有课程-数据库连接成功");
            }
            /*String sql="select course_info.*,user.name as teacher_name from course_info"
                    +"JOIN user ON course_info.teacher_id=user.username"
                    +"WHERE (id IN (SELECT course_id FROM class_course WHERE classname in(SELECT classname FROM user WHERE username=?))"
                    +"or(id IN (SELECT course_id FROM student_course WHERE student_id=?))";*/
 /*String sql = "SELECT course_info.*,`user`.`name` AS teacher_name FROM course_info\n" +
"JOIN user ON course_info.teacher_id=`user`.username  WHERE (id in (SELECT course_id FROM class_course WHERE classname in (SELECT classname FROM `user` WHERE username=?)))or(id in(SELECT course_id from student_course WHERE student_id=?));";*/
 /*String sql = "select course_info.*, user.name as teacher_name from course_info"
                    + " join user on course_info.teacher_id=user.username "
                    + "where (id in(select course_id from class_course where classname in (select classname from user where username=?))) "
                    + "or (id in (select course_id from student_course where student_id=?))";*/
            String sql = "SELECT course_info.* ,`user`.`name` AS teacher_name FROM course_info,`user`,student_course WHERE `user`.username=course_info.teacher_id AND student_course.course_id=course_info.id AND student_course.student_id=?;";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, username);
            //stm.setString(2,username);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                int type = rs.getInt("type");
                float credit = rs.getFloat("credit");
                String book = rs.getString("book");
                String teacherId = rs.getString("teacher_id");
                String teacherName = rs.getString("teacher_name");
                String classroom = rs.getString("classroom");
                String time = rs.getString("time");
                CourseInfo c = new CourseInfo(id, name, type, credit, book, teacherId, teacherName, classroom, time);
                ret.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ret.toArray(new CourseInfo[0]);
    }

    public boolean ClassCancel(String userid,String cid) {
        //学生删除选课
        PreparedStatement stmt = null;
        Connection con = null;
        //ResultSet rst = null;
        boolean result = false;
        try {
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("取消选课-数据库连接成功");
            }
            String sql="delete from student_course where student_id=? AND course_id=?;";
            PreparedStatement stm=con.prepareStatement(sql);
            stm.setString(1,userid);
            stm.setString(2,cid);
            stm.execute();
            result=true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return result;
    }
    
    public CourseInfo[] queryAllElectiveCourse()
    {//查询所有选修课
        ArrayList<CourseInfo> ret = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            if (con != null) {
                System.out.println("查询所有选修课-数据库连接成功");
            }
            String sql = "SELECT course_info.* ,`user`.`name` AS teacher_name FROM course_info,`user`WHERE `user`.username=course_info.teacher_id AND course_info.type=1;";
            PreparedStatement stm = con.prepareStatement(sql);
            //stm.setString(1, username);
            //stm.setString(2,username);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                int type = rs.getInt("type");
                float credit = rs.getFloat("credit");
                String book = rs.getString("book");
                String teacherId = rs.getString("teacher_id");
                String teacherName = rs.getString("teacher_name");
                String classroom = rs.getString("classroom");
                String time = rs.getString("time");
                CourseInfo c = new CourseInfo(id, name, type, credit, book, teacherId, teacherName, classroom, time);
                ret.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ret.toArray(new CourseInfo[0]);
    }
    
    public CourseInfo[] searchEvent(String searchid)
    {//查询课程模糊查询
        
        ArrayList<CourseInfo> ret = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            if (con != null) {
                System.out.println("学生查询课程-数据库连接成功");
            }
            //String sql = "SELECT course_info.* ,`user`.`name` AS teacher_name FROM course_info,`user`WHERE `user`.username=course_info.teacher_id AND course_info.`name` LIKE ?;";
            String sql="SELECT course_info.* ,`user`.`name` AS teacher_name FROM course_info,`user`WHERE `user`.username=course_info.teacher_id AND course_info.type=1 AND course_info.`name` LIKE ? " +
" UNION " +
"SELECT course_info.* ,`user`.`name` AS teacher_name FROM course_info,`user`WHERE `user`.username=course_info.teacher_id AND course_info.type=1 AND course_info.id LIKE ? ";
            PreparedStatement stm = con.prepareStatement(sql);
            String tmp="%"+searchid+"%";
            stm.setString(1, tmp);
            stm.setString(2, tmp);
            //stm.setString(2,username);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                int type = rs.getInt("type");
                float credit = rs.getFloat("credit");
                String book = rs.getString("book");
                String teacherId = rs.getString("teacher_id");
                String teacherName = rs.getString("teacher_name");
                String classroom = rs.getString("classroom");
                String time = rs.getString("time");
                CourseInfo c = new CourseInfo(id, name, type, credit, book, teacherId, teacherName, classroom, time);
                ret.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ret.toArray(new CourseInfo[0]);
    }
    
    public boolean selectCourse(String studentid,String cid)
    {//学生选课
        PreparedStatement stmt = null;
        Connection con = null;
        //ResultSet rst = null;
        boolean result = false;
        try {
            con=ConnectionManager.getConnection();
            if (con != null) {
                System.out.println("插入学生选课记录-数据库连接成功");
            }
            String sql="insert into student_course(course_id,student_id) values(?,?);";
            PreparedStatement stm=con.prepareStatement(sql);
            stm.setString(1,cid);
            stm.setString(2,studentid);
            stm.execute();
            result=true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return result;
    }
    
    public CourseInfo[] queryTeacherAllCourses(String teacherid)
    {//查询教师所有课程
        ArrayList<CourseInfo> ret = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            if (con != null) {
                System.out.println("查询教师所有课程-数据库连接成功");
            }
            String sql = "SELECT course_info.* ,`user`.`name` AS teacher_name FROM course_info,`user` WHERE `user`.username=course_info.teacher_id AND `user`.username=?;";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, teacherid);
            //stm.setString(2,username);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                int type = rs.getInt("type");
                float credit = rs.getFloat("credit");
                String book = rs.getString("book");
                String teacherId = rs.getString("teacher_id");
                String teacherName = rs.getString("teacher_name");
                String classroom = rs.getString("classroom");
                String time = rs.getString("time");
                CourseInfo c = new CourseInfo(id, name, type, credit, book, teacherId, teacherName, classroom, time);
                ret.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ret.toArray(new CourseInfo[0]);
    }
    
    public ArrayList<User>whoChooseCourse(String cid)
    {//选修某选修课的学生信息
        ArrayList<User>ret=new ArrayList<>();
        Connection con=null;
        ResultSet rs=null;
        try{
            //建立连接
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("查询某个选修课选修学生-数据库连接成功");
            }
            //创建查询对象
            String sql="SELECT `user`.*FROM `user`,course_info,student_course WHERE student_course.student_id=username AND  course_info.id=student_course.course_id and course_info.id=?;";
            PreparedStatement stm=con.prepareStatement(sql);
            stm.setString(1, cid);
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
    
    public boolean CreateCourse(CourseInfo courseInfo)
    {//创建课程
        boolean ret=false;
        Connection con=null;
        //ResultSet rs=null;
        try{
            //建立连接
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("创建课程-数据库连接成功");
            }
            //创建查询对象
            String sql="insert into course_info(id,`name`,type,credit,book,teacher_id,classroom,time) values(?,?,?,?,?,?,?,?)";
            PreparedStatement stm=con.prepareStatement(sql);
            stm.setString(1,courseInfo.getId());
            stm.setString(2,courseInfo.getName());
            stm.setInt(3, courseInfo.getType());
            stm.setFloat(4, courseInfo.getCredit());
            stm.setString(5, courseInfo.getBook());
            stm.setString(6, courseInfo.getTeacherId());
            stm.setString(7, courseInfo.getClassroom());
            stm.setString(8, courseInfo.getTime());
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
    
    public boolean UpdateCourse(CourseInfo courseInfo)
    {//更新课程信息
        boolean ret=false;
        Connection con=null;
        //ResultSet rs=null;
        try{
            //建立连接
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("更新课程信息-数据库连接成功");
            }
            //创建查询对象
            String sql="UPDATE course_info SET `name`=?,type=?,credit=?,book=?,teacher_id=?,classroom=?,time=? WHERE id = ?;";
            PreparedStatement stm=con.prepareStatement(sql);      
            stm.setString(1,courseInfo.getName());
            stm.setInt(2, courseInfo.getType());
            stm.setFloat(3, courseInfo.getCredit());
            stm.setString(4, courseInfo.getBook());
            stm.setString(5, courseInfo.getTeacherId());
            stm.setString(6, courseInfo.getClassroom());
            stm.setString(7, courseInfo.getTime());
            stm.setString(8,courseInfo.getId());
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

    public boolean insertClass(String cid,String cname) {
        //班级添加必修课
        boolean ret=false;
        Connection con=null;
        //ResultSet rs=null;
        try{
            //建立连接
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("插入必修课班级表记录-数据库连接成功");
            }
            //创建查询对象
            String sql="insert into class_course(course_id,classname) values(?,?);";
            PreparedStatement stm=con.prepareStatement(sql);
            stm.setString(1,cid);
            stm.setString(2,cname);
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
    public void DeleteClassCourse(String cid)
    {//删除班级必修课
        Connection con=null;
        //ResultSet rs=null;
        try{
            //建立连接
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("删除课程-数据库连接成功");
            }
            //创建查询对象
            String sql="delete from class_course where course_id=?;";
            PreparedStatement stm=con.prepareStatement(sql);
            stm.setString(1,cid);
            //执行
            //rs=stm.executeQuery();
            stm.execute();
            //ret=true;
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
    }
    
    public boolean StudentClassDelets(String cid) {
        //学生删除选课
        PreparedStatement stmt = null;
        Connection con = null;
        //ResultSet rst = null;
        boolean result = false;
        try {
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("删除该课程相关的学生选课记录-数据库连接成功");
            }
            String sql="delete from student_course where course_id=?;";
            PreparedStatement stm=con.prepareStatement(sql);
            stm.setString(1,cid);
            stm.execute();
            result=true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return result;
    }

    public String queryAllCourseTime(String tid)
    {//查询所有课程时间
        Connection con=null;
        ResultSet rs=null;
        String result = null;
        try{
            //建立连接
            String conStr="jdbc:mysql://127.0.0.1:3306/cms?user=root&password=123456";
            con=DriverManager.getConnection(conStr);
            //con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("查询所有课程时间-数据库连接成功");
            }
            //创建查询对象
            String sql="SELECT course_info.time FROM course_info WHERE teacher_id=?;";
            PreparedStatement stm=con.prepareStatement(sql);
            stm.setString(1,tid);
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
        return result;
    }
    
    public boolean StudentCourseDelete(String userid) {
        //删除账号先删除与之关联的课程信息
        PreparedStatement stmt = null;
        Connection con = null;
        //ResultSet rst = null;
        boolean result = false;
        try {
            
            con=ConnectionManager.getConnection();
            if(con!=null)
            {
                System.out.println("删除账号先删除与之关联的课程信息-数据库连接成功");
            }
            String sql="delete from student_course where student_id=?;";
            PreparedStatement stm=con.prepareStatement(sql);
            stm.setString(1,userid);
            stm.execute();
            result=true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return result;
    }
    
    
}
