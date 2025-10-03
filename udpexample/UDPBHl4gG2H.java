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
public class UDPBHl4gG2H {
    public static void main(String[] args) throws Exception {
        DatagramSocket client = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("203.162.10.109");
        int sP = 2207;
        String code = ";B22DCAT249;BHl4gG2H";
        DatagramPacket dpSend = new DatagramPacket(code.getBytes(),code.length(),sA,sP);
        client.send(dpSend);
        
        byte[] buffer = new byte[1024];
        DatagramPacket dpReceive = new DatagramPacket(buffer, buffer.length);
        client.receive(dpReceive);
        
        String in = new String(dpReceive.getData()).trim().replace("\r", "");
        String[] tmp = in.split(";");
        String idSubmit = tmp[0];
        String[] numbers = tmp[1].split(",");
        int len = numbers.length;
        List<Integer> listNum = new ArrayList<>();
        for (String n : numbers){
            listNum.add(Integer.parseInt(n));
        }
        Collections.sort(listNum);
        Integer secondMax = listNum.get(len-2);
        Integer secondMin = listNum.get(1);
        String result = idSubmit + ";" +secondMax + "," + secondMin;
        DatagramPacket dpSubmit = new DatagramPacket(result.getBytes(),result.length(),sA,sP);
        client.send(dpSubmit);
    }
}
