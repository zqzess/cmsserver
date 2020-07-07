/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import cms.net.NetUtil;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author zqzess
 */
public class NetClient {
    public static void main (String[] args) throws IOException{
        System.out.println("Net Client");
        Scanner sc=new Scanner(System.in);
        System.out.println("connect to server?");
        String s=sc.nextLine();
        Socket sock=null;
        DataInputStream inStm=null;
        DataOutputStream outStm=null;
        try{
        sock =new Socket("192.168.232.233",20000);
        //sock =new Socket("10.10.105.32",20000);
        //sock=new Socket("127.0.0.1",20000);
        //sock=new Socket("106.52.47.41",20000);
        System.out.println("connect to server success!");
        NetUtil.displaySocketAdddress(sock);
 //       System.out.println("input message sending to server: ");
  //      s=sc.nextLine();
         //s="1833001\n123";
         //s="query_student_all_courses\n1833003";
         //s="queryClass";
         //s="queryallelectivecourse";
         //s="searchevent\n语";
         //s="querycourseClass\nc001";
         //s="TeacherDeleteCourse\nce006";
         //s="AdminqueryAllUsers";
         s="searchevent\n5";
         s="selecClassInfo\n计网L1801";
         outStm=new DataOutputStream(sock.getOutputStream());
        outStm.writeUTF(s);
        System.out.println("data is sended to server!");
        inStm=new DataInputStream(sock.getInputStream());
        s=inStm.readUTF();
        System.out.println("recv: "+s);
        }catch(IOException ex){
            ex.printStackTrace();
        }finally{
            try{
            if(inStm!=null)inStm.close();
            if(outStm!=null)outStm.close();
            if(sock!=null)sock.close();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
        
    }
}
