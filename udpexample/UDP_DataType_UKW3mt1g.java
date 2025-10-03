/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpexample;
import java.io.*;
import java.math.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author Lenovo
 */
public class UDP_DataType_UKW3mt1g {
    public static void main(String[] args) throws Exception {
        DatagramSocket client = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("203.162.10.109");
        int sP = 2207;
        String code = ";B22DCAt249;UKW3mt1g";
        DatagramPacket dpSend = new DatagramPacket(code.getBytes(), code.length(), sA, sP);
        client.send(dpSend);
        System.out.println("Da gui len server");
        
        byte[] buffer = new byte[1024];
        DatagramPacket dpReceive = new DatagramPacket(buffer, buffer.length);
        client.receive(dpReceive);
        String st = new String(dpReceive.getData()).trim().replace("\r", "");
        System.out.println(st);
        String[] tmp = st.split(";");
        String idSubmit = tmp[0];
        String[] numbers = tmp[1].split(",");
        List<Integer> listNum = new ArrayList<>();
        int len = numbers.length;
        for (String n : numbers ){
            listNum.add(Integer.parseInt(n));
        }
        Collections.sort(listNum);
        Integer max = listNum.get(len-1);
        Integer min = listNum.get(0);
        String result = idSubmit + ";"+max+","+min;
        DatagramPacket dpSubmit = new DatagramPacket(result.getBytes(),result.length(),sA,sP);
        client.send(dpSubmit);
        
    }
}
