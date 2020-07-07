/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import cms.dao.User;

/**
 *
 * @author zqzess
 */
public class TestData {
    String v;
    int i;
    String[] arr;
    User u;
    public TestData()
    {
        v="abc";
        i=1;
        arr=new String[]{"apple","ori","xx"};
        u=new User("1833001",0,"Tom","021003","计网","123",0);
    }
}
