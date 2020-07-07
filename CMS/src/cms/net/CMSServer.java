package cms.net;

import cms.dao.Classinfo;
import cms.dao.ClassinfoDao;
import cms.dao.CourseDao;
import cms.dao.CourseInfo;
import cms.dao.DaoFactory;
import cms.dao.User;
import cms.dao.UserDao;
import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author zqzess
 */
public class CMSServer {

    static String logon(String[] arrStrings) {
//登录
        long threadId=Thread.currentThread().getId();
        System.out.println(threadId+" login-->"+new Date().toString());
        String retMsg;
        String username = arrStrings[1];
        String password = arrStrings[2];
        UserDao userDao = DaoFactory.getUserDao();
        User u = userDao.queryUser(username);
        String errMsg = "";

        if (u == null) {
            errMsg = "用户未注册";

        } else {

            if (u.getState() != 1) {
                errMsg = "用户待审核或者审核";

            }
            if (!u.getPassword().equals(password)) {
                errMsg = "密码不正确";

            }
        }
        if (errMsg.isEmpty()) {
            int userType = u.getType();
            retMsg = "Y\n" + userType;
        } else {
            retMsg = "N\n" + errMsg;
        }
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(threadId+" login<--"+new Date().toString());
        return retMsg;
    }

    static String registerUser(String[] arrStrings) {
//注册
        String retMsg = "";
        String userType = arrStrings[1];
        String userId = arrStrings[2];
        String username = arrStrings[3];
        String phone = arrStrings[4];
        String classname = arrStrings[5];
        String password = arrStrings[6];
        UserDao userDao = DaoFactory.getUserDao();
        if (userDao.queryUser(userId) != null) {
            retMsg = "N\n用户名:" + userId + "已注册";
            return retMsg;
        }
        int iType = Integer.parseInt(userType);
        User newUser = null;
        if (iType == 0) {
            newUser = new User(userId, iType, username, phone, classname, password, 0);
        } else if (iType == 1) {

            newUser = new User(userId, iType, username, phone, null, password, 0);
        }

        boolean result = userDao.createUser(newUser);
        if (result) {
            retMsg = "Y\n注册成功，请等待管理员审核！";
        } else {
            retMsg = "N\n注册失败，请稍后再试";
        }
        return retMsg;

    }

    static String qurtyAllClass() {
//查询班级
        String retMsg = "";
        ClassinfoDao classDao = DaoFactory.getClassinfoDao();
        Classinfo[] allClass = classDao.queryAllClass();

        for (Classinfo i : allClass) {
            retMsg += i.getName();
            retMsg += "\n";
        }
        return retMsg;
    }

    static String queryWaitingApprovalUsers()
    {//查询所有待审核用户
        UserDao userDao=DaoFactory.getUserDao();
        ArrayList<User> users=userDao.queryWaitingApprovalUsers();
        QueryWaitingApprovalResult retObj=new QueryWaitingApprovalResult();
        retObj.success=true;
        retObj.msg="";
        retObj.users=users;
        
        Gson gson=new Gson();
        return  gson.toJson(retObj);
    }
    
    static  String userApproval(String[] arr)
    {//用户通过审核
        String userid=arr[1];
        String approvalAction=arr[2];
        
        UserDao userDao=DaoFactory.getUserDao();
        if(approvalAction.equals("1"))
        {
            userDao.approvalUser(userid, true);
            
        }else if(approvalAction.equals("0"))
        {
            userDao.approvalUser(userid, false);
        }
        return  "1";
    }
    
    static String queryStudentAllCourses(String[] arr)
    {//查询学生所有课程
        CourseDao courseDao=DaoFactory.getCourseDao();
        CourseInfo[] courses=courseDao.queryStudentAllCourses(arr[1]);
        QueryStudentAllCourseResult result=new QueryStudentAllCourseResult();
        result.success=true;
        result.msg="";
        result.courses=courses;
        Gson gson=new Gson();
        return  gson.toJson(result); 
    }
    static String classCancel(String[] arr)
    {//取消选课
        //取消选修课
        String userid=arr[1];
        String cid=arr[2];
        
        CourseDao courseDao=DaoFactory.getCourseDao();  
        boolean result = courseDao.ClassCancel(userid, cid);
        return "1";
    }
    static String queryAllElectiveCourse(String[] arr)
    {//查询所有选修课
        CourseDao courseDao=DaoFactory.getCourseDao();
        CourseInfo[] courses=courseDao.queryAllElectiveCourse();
        QueryStudentAllCourseResult result=new QueryStudentAllCourseResult();
        result.success=true;
        result.msg="";
        result.courses=courses;
        Gson gson=new Gson();
        return  gson.toJson(result); 
    }
    static String searchEvent(String[] arr)
    {//搜索
        String searchid=arr[1];
        CourseDao courseDao=DaoFactory.getCourseDao();
        CourseInfo[] courses=courseDao.searchEvent(searchid);
        QueryStudentAllCourseResult result=new QueryStudentAllCourseResult();
        result.success=true;
        result.msg="";
        result.courses=courses;
        Gson gson=new Gson();
        return  gson.toJson(result); 
    }
    static String selectCourse(String[] arr)
    {//选课
        String retMsg = "";
        String cid=arr[1];
        String studentid=arr[2];
        CourseDao courseDao=DaoFactory.getCourseDao();  
        boolean result = courseDao.selectCourse(studentid, cid);
        if (!result) {
            retMsg = "N";
        }else
        {
            retMsg = "Y";
        }
        return retMsg;
    }
    static String queryTeacherAllCourses(String[] arr)
    {//查询教师所有课程
        CourseDao courseDao=DaoFactory.getCourseDao();
        CourseInfo[] courses=courseDao.queryTeacherAllCourses(arr[1]);
        QueryStudentAllCourseResult result=new QueryStudentAllCourseResult();
        result.success=true;
        result.msg="";
        result.courses=courses;
        Gson gson=new Gson();
        return  gson.toJson(result); 
    }
    static String whoChooseCourse(String[] arr)
    {//查询选修课选修的学生
        String cid=arr[1];
        CourseDao courseDao=DaoFactory.getCourseDao();
        ArrayList<User> users=courseDao.whoChooseCourse(cid);
        QueryWaitingApprovalResult retObj=new QueryWaitingApprovalResult();
        retObj.success=true;
        retObj.msg="";
        retObj.users=users;
        
        Gson gson=new Gson();
        return  gson.toJson(retObj);
    }
    static String queryClass(String[] arr)
    {//查询必修课程班级
        String retMsg = "";
        String retMsString="";
        String cid=arr[1];
        ClassinfoDao classDao = DaoFactory.getClassinfoDao();
        Classinfo[] Class = classDao.queryClass(cid);
        for (Classinfo i : Class) {
            retMsg += i.getName();
            retMsg += ",";
                     
        }
        retMsString=retMsg.substring(0, retMsg.length()-1);
        return retMsString;
    }
    static String teacherDeleteCourse(String[] arr)
    {//教师删除课程
        String cid=arr[1];
        String typetmp=arr[2];
        int type=Integer.parseInt(typetmp);
        ClassinfoDao classinfoDao=DaoFactory.getClassinfoDao();
        CourseDao courseDao=DaoFactory.getCourseDao(); 
        if(type==0)
        {
            //必修课，先删除班级与必修课关系表记录，再删除课程
            courseDao.DeleteClassCourse(cid);
            classinfoDao.teacherDeleteCourse(cid);
        }
        if(type==1)
        {
            //选修课，先删除学生与选修课表记录，再删除课程
            courseDao.StudentClassDelets(cid);
            classinfoDao.teacherDeleteCourse(cid);
        }
            
        return "1";
    }
    static String createCourse(String[] arr)
    {//创建课程
        String retMsg = "";
        
        String cid=arr[1];
        String cname=arr[2];
        String ctypetmp=arr[3];
        int ctype=Integer.parseInt(ctypetmp);
        String credittmp=arr[4];
        String cbook=arr[5];
        String teacherid=arr[6];
        String classroom=arr[7];
        String ctime=arr[8];
        String classname=arr[9];
        String counttmp=arr[10];
        int count=Integer.parseInt(counttmp);
        CourseInfo newcourseInfo=null;
        CourseDao courseDao=DaoFactory.getCourseDao();
        if(ctype==0)
        {//必修有班级
            float credit = Float.parseFloat(credittmp);
            newcourseInfo=new CourseInfo(cid, cname, ctype, credit, cbook, teacherid, teacherid, classroom, ctime);
            boolean result=courseDao.CreateCourse(newcourseInfo);
            if(count>1)
            {//必修课班级数量大于1
                
                String[] classnamearr=classname.split(",");
                for(int i=0;i<count;i++)
                {
                    courseDao.insertClass(cid, classnamearr[i]);
                }
            }else if(count==1)
            {//必修课班级数量等于1
                boolean result2=courseDao.insertClass(cid, classname);
            }
        }else if(ctype==1)
        {//选修课无班级
            
            float credit = Float.parseFloat(credittmp);
            newcourseInfo=new CourseInfo(cid, cname, ctype, credit, cbook, teacherid, teacherid, classroom, ctime);
            //CourseDao courseDao=DaoFactory.getCourseDao();
            boolean result=courseDao.CreateCourse(newcourseInfo);
        }
        return retMsg;
    }
    static String editCourseInfo(String[] arr)
    {//更新课程信息
        String retMsg="";
        String cid=arr[1];
        String cname=arr[2];
        String ctypetmp=arr[3];
        int ctype=Integer.parseInt(ctypetmp);
        String credittmp=arr[4];
        String cbook=arr[5];
        String teacherid=arr[6];
        String classroom=arr[7];
        String ctime=arr[8];
        String classname=arr[9];
        String counttmp=arr[10];
        int count=Integer.parseInt(counttmp);
        CourseInfo newcourseInfo=null;
        CourseDao courseDao=DaoFactory.getCourseDao();
        if(ctype==0)
        {//必修有班级
            float credit = Float.parseFloat(credittmp);
            newcourseInfo=new CourseInfo(cid, cname, ctype, credit, cbook, teacherid, teacherid, classroom, ctime);
            boolean result=courseDao.UpdateCourse(newcourseInfo);
            if(count>1)
            {//必修，班级多个，先删除所有选课班级记录，再重新录入
                String[] classnamearr=classname.split(",");
                courseDao.DeleteClassCourse(cid);//必须课记录
                //courseDao.StudentClassDelets(cid);//选修课记录
                for(int i=0;i<count;i++)
                {
                    courseDao.insertClass(cid, classnamearr[i]);
                }
            }else if(count==1)
            {//必修，班级一个,先删除所有选课班级记录，再重新录入
                courseDao.DeleteClassCourse(cid);
                boolean result2=courseDao.insertClass(cid, classname);
            }
            
        }else if(ctype==1)
        {//选修
            float credit = Float.parseFloat(credittmp);
            newcourseInfo=new CourseInfo(cid, cname, ctype, credit, cbook, teacherid, teacherid, classroom, ctime);
            //CourseDao courseDao=DaoFactory.getCourseDao();
            boolean result=courseDao.UpdateCourse(newcourseInfo);
            //courseDao.DeleteClassCourse(cid);
        }
        return retMsg;
    }
    static String queryAllCourseTime(String[] arr)
    {
        //查询数据库，返回所有课程时间
        String retMsg="";
        String tid=arr[1];
        CourseDao courseDao=DaoFactory.getCourseDao();
        
        retMsg=courseDao.queryAllCourseTime(tid);
        return retMsg;
    }
    static String queryAllUsers()
    {//查询所有用户
        UserDao userDao=DaoFactory.getUserDao();
        ArrayList<User> users=userDao.queryAllUsers();
        QueryWaitingApprovalResult retObj=new QueryWaitingApprovalResult();
        retObj.success=true;
        retObj.msg="";
        retObj.users=users;
        
        Gson gson=new Gson();
        return  gson.toJson(retObj);
    }
    static String AdminupdateUserInfo(String[] arr)
    {//更新用户信息
        String retMsg="";
        String userId = arr[1];
        String userType = arr[2];
        String username = arr[3];
        String phone = arr[4];
        String classname = arr[5];
        String password = arr[6];
        String state=arr[7];
        UserDao userDao = DaoFactory.getUserDao();
        
        int iType = Integer.parseInt(userType);
        int iState=Integer.parseInt(state);
        
        if(iType==0)
        {
            User newUser = null;
            newUser = new User(userId, iType, username, phone, classname, password, iState);
            boolean result=userDao.AdminupdateUserInfo(newUser);
        }else if(iType==1)
        {
            User newUser = null;
            newUser = new User(userId, iType, username, phone, null, password, iState);
            boolean result=userDao.AdminupdateUserInfo(newUser);
        }
        
        return retMsg;
    }
    static String AdminDeleteUser(String[] arr)
    {//删除用户,需要先删除外键（删除学生与选课表记录）
        String retMsg="";
        String userid=arr[1];
        CourseDao courseDao=DaoFactory.getCourseDao();
        UserDao userDao = DaoFactory.getUserDao();
        courseDao.StudentCourseDelete(userid);
        userDao.AdminDeleteUser(userid);
              
       return retMsg; 
    }
    static String AdminquerySearchType(String[] arr)
    {
        //身份类型查询
        String retMsg="";
        String typetmp=arr[1];
        int type=Integer.parseInt(typetmp);
        UserDao userDao=DaoFactory.getUserDao();
        ArrayList<User> users=userDao.AdminquerySearchType(type);
        QueryWaitingApprovalResult retObj=new QueryWaitingApprovalResult();
        retObj.success=true;
        retObj.msg="";
        retObj.users=users;
        
        Gson gson=new Gson();
        return  gson.toJson(retObj);
    }
    static String AdminquerySearcrchEvent(String[] arr)
    {
        //管理员查询事件
        String retMsg="";
        String searchid=arr[1];
        UserDao userDao=DaoFactory.getUserDao();
        ArrayList<User> users=userDao.AdminquerySearcrchEvent(searchid);
        QueryWaitingApprovalResult retObj=new QueryWaitingApprovalResult();
        retObj.success=true;
        retObj.msg="";
        retObj.users=users;
        
        Gson gson=new Gson();
        return  gson.toJson(retObj);
    }
    static String createClass(String[] arr)
    {
        //创建班级
        String classname=arr[1];
        String retMsg="";
        ClassinfoDao classinfoDao=DaoFactory.getClassinfoDao();
        classinfoDao.createClass(classname);
        return retMsg;
    }
    static String queryClassSearch(String[] arr)
    {
        //搜索班级
        String retMsg = "";
        String searchid=arr[1];
        ClassinfoDao classDao = DaoFactory.getClassinfoDao();
        Classinfo[] allClass = classDao.queryClassSearch(searchid);

        for (Classinfo i : allClass) {
            retMsg += i.getName();
            retMsg += "\n";
        }
        return retMsg;
    }
    static String ResetPwdVerify(String[] arr)
    {
        //验证账号是否存在
        String retMsg;
        String username = arr[1];
        UserDao userDao = DaoFactory.getUserDao();
        User u = userDao.ResetPwdVerify(username);
        String errMsg = "";

        if (u == null) {
            errMsg = "用户未注册";
            retMsg = "N";
        } else {
            retMsg = "Y";
        }
        return retMsg;
        
    }
    static String updatePwd(String[] arr)
    {
        //修改密码
        String retMsg="";
        String userid=arr[1];
        String pwd=arr[2];
        UserDao userDao = DaoFactory.getUserDao();
        userDao.updatePwd(userid,pwd);
        return retMsg;
    }
    static String selecClassInfo(String[] arr)
    {//查询班级详细信息
        String retMsg="";
        String classname=arr[1];
        ClassinfoDao classinfoDao=DaoFactory.getClassinfoDao();
        int count=classinfoDao.selecClassInfoCount(classname);
        String course=classinfoDao.selecClassInfoCourse(classname);
        course=course.substring(0,course.length()-1);
        retMsg=count+"-"+course;
        return retMsg;
    }
    static String updateClassName(String[] arr)
    {//修改班级名称,先创建修改后的班级名记录，再修改学生绑定的班级为新班级名，再修改班级绑定的必修课记录，最后删除旧班级
        String retMsg="";
        String oldClassname=arr[1];
        String newClassname=arr[2];
        ClassinfoDao classinfoDao=DaoFactory.getClassinfoDao();
        classinfoDao.createClass(newClassname);//创建新班级
        classinfoDao.updateStudentClassname(oldClassname,newClassname);//修改学生绑定的班级为新班级名
        classinfoDao.updateClassCourse(oldClassname,newClassname);//修改班级绑定的必修课记录
            classinfoDao.deleteClassname(oldClassname);//删除旧班级
        return retMsg;
    }
    public static void main(String[] args) {
//服务器主程序
        System.out.println("服务器启动中");
        System.out.println("main thread id:"+Thread.currentThread().getId());
        ServerSocket serverSocket = null;

        Scanner sc = new Scanner(System.in);
        try {
            serverSocket = new ServerSocket(20000);
            System.out.println("服务器启动成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            while (true) {
                final Socket sock = serverSocket.accept();
                System.out.println("客户端连接成功");
                //NetUtil.displaySocketAdddress(sock);

                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DataOutputStream out = null;
                        DataInputStream in = null;

                        try {
                            out = new DataOutputStream(sock.getOutputStream());
                            in = new DataInputStream(sock.getInputStream());
                            String string = in.readUTF();
                            String[] arrStrings = string.split("\n");
                            String retMsg = "";
                            if (arrStrings[0].equals("logon")) {
                                //登录
                                retMsg = logon(arrStrings);

                            } else if (arrStrings[0].equals("queryClass")) {
                                //查询数据库班级信息
                                retMsg = qurtyAllClass();
                            } else if (arrStrings[0].equals("register_user")) {
                                //注册
                                retMsg = registerUser(arrStrings);
                            }else if(arrStrings[0].equals("queryApprovalUsers"))
                            {
                                retMsg=queryWaitingApprovalUsers();
                            }else if(arrStrings[0].equals("userApproval"))
                            {
                                retMsg=userApproval(arrStrings);
                            }else if(arrStrings[0].equals("query_student_all_courses"))
                            {
                                retMsg=queryStudentAllCourses(arrStrings);
                            }else if(arrStrings[0].equals("cancelClass"))
                            {
                                retMsg=classCancel(arrStrings);
                            }else if(arrStrings[0].equals("queryallelectivecourse"))
                            {
                                retMsg=queryAllElectiveCourse(arrStrings);
                            }else if(arrStrings[0].equals("searchevent"))
                            {
                                retMsg=searchEvent(arrStrings);
                            }else if(arrStrings[0].equals("selectcourse"))
                            {
                                retMsg=selectCourse(arrStrings);
                            }else if(arrStrings[0].equals("query_teacher_all_courses"))
                            {
                                retMsg=queryTeacherAllCourses(arrStrings);
                            }else if(arrStrings[0].equals("queryStudentChoose"))
                            {
                                retMsg=whoChooseCourse(arrStrings);
                            }else if(arrStrings[0].equals("querycourseClass"))
                            {
                                retMsg=queryClass(arrStrings);
                            }else if(arrStrings[0].equals("TeacherDeleteCourse"))
                            {
                                retMsg=teacherDeleteCourse(arrStrings);
                            }else if(arrStrings[0].equals("createCourse"))
                            {
                                retMsg=createCourse(arrStrings);
                            }else if(arrStrings[0].equals("editCourseInfo"))
                            {
                                retMsg=editCourseInfo(arrStrings);
                            }else if(arrStrings[0].equals("queryAllCourseTime"))
                            {
                                retMsg=queryAllCourseTime(arrStrings);
                            }else if(arrStrings[0].equals("AdminqueryAllUsers"))
                            {
                                retMsg=queryAllUsers();
                            }else if(arrStrings[0].equals("AdminupdateUserInfo"))
                            {
                                retMsg=AdminupdateUserInfo(arrStrings);
                            }else if(arrStrings[0].equals("AdminDeleteUser"))
                            {
                                retMsg=AdminDeleteUser(arrStrings);
                            }else if(arrStrings[0].equals("AdminquerySearchType"))
                            {
                                retMsg=AdminquerySearchType(arrStrings);
                            }else if(arrStrings[0].equals("AdminquerySearcrchEvent"))
                            {
                                retMsg=AdminquerySearcrchEvent(arrStrings);
                            }else if(arrStrings[0].equals("createClass"))
                            {
                                retMsg=createClass(arrStrings);
                            }else if(arrStrings[0].equals("queryClassSearch"))
                            {
                                retMsg=queryClassSearch(arrStrings);
                            }else if(arrStrings[0].equals("ResetPwdVerify"))
                            {
                                retMsg=ResetPwdVerify(arrStrings);
                            }else if(arrStrings[0].equals("updatePwd"))
                            {
                                retMsg=updatePwd(arrStrings);
                            }else if(arrStrings[0].equals("selecClassInfo"))
                            {
                                retMsg=selecClassInfo(arrStrings);
                            }else if(arrStrings[0].equals("updateClassName"))
                            {
                                retMsg=updateClassName(arrStrings);
                            }
                            out.writeUTF(retMsg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (in != null) {
                                    in.close();
                                }
                                if (out != null) {
                                    out.close();
                                }
                                sock.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
                th.start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
