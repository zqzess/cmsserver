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
public class DaoFactory {
    public static UserDao getUserDao()
    {
        return new UserDaoImp() ;
    }
    public static ClassinfoDao getClassinfoDao()
    {
        return  new ClassinfoImp();
    }
    
    public static CourseDao getCourseDao()
    {
        return new CourseDaoImp();
    }
    
}
