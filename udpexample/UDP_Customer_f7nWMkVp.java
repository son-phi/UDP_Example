/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpexample;
import java.io.*;
import java.net.*;
import java.util.*;
import UDP.Customer;
/**
 *
 * @author Lenovo
 */
public class UDP_Customer_f7nWMkVp {
    public static void main(String[] args) throws Exception {
        DatagramSocket client = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("203.162.10.109");
        int sP = 2209;
        String code = ";B22DCAT249;f7nWMkVp";
        client.send(new DatagramPacket(code.getBytes(), code.length(), sA, sP));
        
        byte[] buffer = new byte[1024];
        DatagramPacket dpReceive = new DatagramPacket(buffer, buffer.length);
        client.receive(dpReceive);
        
        String reqId = new String(dpReceive.getData(), 0 , 8);
        System.out.println("Request ID:"+reqId);
        
        ByteArrayInputStream bais = new ByteArrayInputStream(dpReceive.getData(), 8, dpReceive.getLength() - 8);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Customer customer = (Customer) ois.readObject();
        System.out.println(customer);
        
        String originalName = customer.getName();
        // name
        String[] tmp = originalName.trim().split("\\s+");
        String name = tmp[tmp.length-1].toUpperCase()+",";
        for(int i = 0 ; i< tmp.length - 1; i++){
            name+= " "+ tmp[i].substring(0, 1).toUpperCase() + tmp[i].substring(1).toLowerCase();
        }
        customer.setName(name);
        
        // dateOfBirth
        String date = customer.getDayOfBirth();
        String[] arrDate = date.trim().split("-");
        String dateFormatted = arrDate[1]+"/"+arrDate[0]+"/"+arrDate[2];
        customer.setDayOfBirth(dateFormatted);

        //username
        String[] arrName = originalName.trim().split("\\s+");
        String nameFormatted = "";
        for(int i =0 ; i< arrName.length -1; i++){
            nameFormatted += Character.toLowerCase(arrName[i].charAt(0));
        }
        nameFormatted += arrName[arrName.length -1].toLowerCase();
        customer.setUserName(nameFormatted);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(customer);
        oos.flush();
        
        byte[] sendData = new byte[8 + baos.size() ];
        System.arraycopy(reqId.getBytes(), 0, sendData, 0, 8);
        System.arraycopy(baos.toByteArray(), 0, sendData, 8, baos.size());
        client.send(new DatagramPacket(sendData, sendData.length, sA, sP));
        client.close();
    }
}
