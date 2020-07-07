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
public interface UserDao {
    public User queryUser(String username);//查询用户
    public boolean createUser(User u);//创建用户
    //public  ArrayList

    public ArrayList<User> queryWaitingApprovalUsers();//查询待审核用户
    
    public  void approvalUser(String userid,boolean action);//审核用户
    
    public ArrayList<User>queryAllUsers();//查询所有用户
    
    public boolean AdminupdateUserInfo(User u);//更新用户信息
    
    public void AdminDeleteUser(String userid);//删除用户
    
    public ArrayList<User>AdminquerySearchType(int searchtype) ;//查询身份类型
    
    public ArrayList<User>AdminquerySearcrchEvent(String searchid);//管理员查询事件
    
    public User ResetPwdVerify(String username);//查询账号是否存在
    
    public void updatePwd(String userid,String pwd);//修改账号
}
