/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpexample;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author Lenovo
 */
public class UDP_String_dpWFdaLS {
    public static void main(String[] args) throws Exception {
        DatagramSocket client = new DatagramSocket();
        
        InetAddress sA = InetAddress.getByName("203.162.10.109");
        int sP = 2208;
        String code = ";B22DCAT249;dpWFdaLS";
        DatagramPacket dpSend = new DatagramPacket(code.getBytes(),code.length(),sA,sP);
        client.send(dpSend);
        System.out.println("Da gui len server");
        
        byte[] buffer = new byte[1024];
        DatagramPacket dpReceive = new DatagramPacket(buffer, buffer.length);
        client.receive(dpReceive);
        String st = new String(dpReceive.getData());
        String[] tmp = st.trim().split(";");
        String iD = tmp[0];
        String[] words = tmp[1].trim().split("\\s+");
        List<String> lstStr = new ArrayList<>();
        for (String w : words){
            String formatted = w.substring(0,1).toUpperCase() +w.substring(1).toLowerCase();
            lstStr.add(formatted);
        }
        String result = iD +";"+ String.join(" ",lstStr);
        System.out.println(result);
        DatagramPacket dpSubmit = new DatagramPacket(result.getBytes(),result.length(),sA,sP);
        client.send(dpSubmit);
    }
}
