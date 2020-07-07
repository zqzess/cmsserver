/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cms.dao;


/**
 *
 * @author zqzess
 */
public interface ClassinfoDao {
    public Classinfo[] queryAllClass();
    public Classinfo[] queryClass(String cid);
    public void teacherDeleteCourse(String cid);
    public void createClass(String classname);
    public Classinfo[] queryClassSearch(String searchid);
    
    public int selecClassInfoCount(String classname);
    public String selecClassInfoCourse(String classname);
    
    public void updateStudentClassname(String oldClassname,String newClassname);
    public void updateClassCourse(String oldClassname,String newClassname);
    public void deleteClassname(String oldClassname);
}
