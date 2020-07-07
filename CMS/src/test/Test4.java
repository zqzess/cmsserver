/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author zqzess
 */
public class Test4 {
    public static void main(String[] args) {
        String aString="1,2,3,4,5,6,7,8,9";
        int d=aString.length();
        int b=((aString.length()-1)/2+1)/3;
        String c=aString.substring(6,11);
        System.out.println("a:"+aString);
        System.out.println("b:"+b);
        System.out.println("c:"+c);
        System.out.println("d:"+d);
    }
    
}
