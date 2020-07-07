/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.google.gson.Gson;

/**
 *
 * @author zqzess
 */
public class TestGson {
    public static void main(String[] args) {
        System.out.println("gson");
        Gson gson=new Gson();
        TestData d=new TestData();
        String s=gson.toJson(d);
        System.out.println(s);
        
        /*String jsonStr="{\"v\":\"abc\",\"i\":1,\"arr\":[\"apple\",\"ori\",\"xx\"],\"u\":{\"uernameString\":\"1833001\",\"type\":0,\"name\":\"Tom\",\"phone\":\"021003\",\"classname\":\"计网\",\"password\":\"123\",\"state\":0}}";
        System.out.println("jsonStr"+jsonStr);
        TestData objFromJson=gson.fromJson(jsonStr, TestData.class);*/ 
        //json转java
        
        
    }
    
}
