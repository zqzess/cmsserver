/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import cms.dao.CourseDao;
import cms.dao.CourseInfo;
import cms.dao.DaoFactory;

/**
 *
 * @author zqzess
 */
public class TestCourseDao {
    public static void main(String[] args) {
        System.out.print("test course dao");
        CourseDao dao=DaoFactory.getCourseDao();
        CourseInfo[] courses= dao.queryStudentAllCourses("1833003");
        System.out.println("end");
    }
}
