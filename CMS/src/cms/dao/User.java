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
public class User {
    String uernameString;
    int type;
    String name;
    String phone;
    String classname;
    String password;
    int state;

    public User(String uernameString, int type, String name, String phone, String classname, String password, int state) {
        this.uernameString = uernameString;
        this.type = type;
        this.name = name;
        this.phone = phone;
        this.classname = classname;
        this.password = password;
        this.state = state;
    }

    public String getUernameString() {
        return uernameString;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getClassname() {
        return classname;
    }

    public String getPassword() {
        return password;
    }

    public int getState() {
        return state;
    }
    
}
