/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cms.dao;

import java.util.ArrayList;

/**
 *
 * @author zqzess
 */
public interface CourseDao {
    CourseInfo[] queryStudentAllCourses(String username);
    boolean ClassCancel(String useridString,String cid);
    CourseInfo[] queryAllElectiveCourse();
    CourseInfo[] searchEvent(String searchid);
    boolean selectCourse(String studentid,String cid);
    CourseInfo[] queryTeacherAllCourses(String teacherid);
    public ArrayList<User> whoChooseCourse(String cid);
    boolean CreateCourse(CourseInfo courseInfo);
    boolean UpdateCourse(CourseInfo courseInfo);
    boolean insertClass(String cid,String cname);
    public void DeleteClassCourse(String cid);
    boolean StudentClassDelets(String cid);
    public String queryAllCourseTime(String tid);
    public boolean StudentCourseDelete(String userid); 
}
