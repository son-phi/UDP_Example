/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpexample;

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;

/**
 *
 * @author Lenovo
 */
public class UDP_String_wQQRiTrY {
    public static void main(String[] args) throws Exception {
        DatagramSocket client = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("203.162.10.109");
        int sP = 2208;
        String code = ";B22DCAT249;wQQRiTrY";
        DatagramPacket dpSend = new DatagramPacket(code.getBytes(),code.length(),sA,sP);
        client.send(dpSend);
        
        byte[] buffer = new byte[1024];
        DatagramPacket dpReceive = new DatagramPacket(buffer,buffer.length);
        client.receive(dpReceive);
       
        String st = new String(dpReceive.getData());
        String[] tmp = st.trim().split(";");
        String iD = tmp[0];
        String[] words = tmp[1].split("\\s+");
        ArrayList<String> listStr = new ArrayList<>();
        for (String w : words){
            listStr.add(w);
        }
//        listStr.sort(Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));
        Collections.sort(listStr, new Comparator<String>(){
            @Override
            public int compare(String s1, String s2){
                return s2.toLowerCase().compareTo(s1.toLowerCase());
            }
        });
        String result = iD +";"+ String.join(",", listStr);
        DatagramPacket dpSubmit = new DatagramPacket(result.getBytes(),result.length(),sA,sP);
        client.send(dpSubmit);
        
    }
}
