/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cms;

import cms.net.NetUtil;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author zqzess
 */
public class NetClient {

    public static void main(String[] args) throws IOException {
        Socket sock = null;
        DataOutputStream out = null;
        DataInputStream in = null;
        System.out.println("客户端");
        Scanner sc = new Scanner(System.in);
        System.out.println("连接服务器?");
        String s = sc.nextLine();
        try {
            sock = new Socket("192.168.31.238", 20000);
            out = new DataOutputStream(sock.getOutputStream());
            in = new DataInputStream(sock.getInputStream());
            System.out.println("连接服务器成功");
            NetUtil.displaySocketAdddress(sock);
            //System.out.println("发送数据:");
            //s = sc.nextLine();
            s="1833002\n123";
            out.writeUTF(s);
            //BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            //writer.write("hello,this is client!");
            //writer.flush();
            System.err.println("数据发送成功");
            s = in.readUTF();
            System.out.println("recv:" + s);
            s = sc.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (sock != null) {
                    sock.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //BufferedReader reader=new BufferedReader(new InputStreamReader(sock.getInputStream()));
    }

}
