/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cms.net;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author zqzess
 */
public class NetUtil {
    public static void displaySocketAdddress(Socket sock)
    {
        InetSocketAddress localAdd=(InetSocketAddress)sock.getLocalSocketAddress();
        InetSocketAddress remoteAddress=(InetSocketAddress)sock.getRemoteSocketAddress();
        System.out.println("local: "+localAdd.getAddress().getHostAddress()+":"+localAdd.getPort());
        System.out.println("remote: "+remoteAddress.getAddress().getHostAddress()+":"+remoteAddress.getPort());
    }
}
