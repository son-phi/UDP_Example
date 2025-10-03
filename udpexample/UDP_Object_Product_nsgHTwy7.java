/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpexample;
import java.io.*;
import java.net.*;
import java.util.*;
import UDP.Product;
/**
 *
 * @author Lenovo
 */
public class UDP_Object_Product_nsgHTwy7 {
    public static void main(String[] args) throws Exception {
        DatagramSocket client = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("203.162.10.109");
        int sP = 2209;
        String code = ";B22DCAT249;nsgHTwy7";
        DatagramPacket dpSend = new DatagramPacket(code.getBytes(),code.length(),sA,sP);
        client.send(dpSend);
        
        byte[] buffer = new byte[1024];
        DatagramPacket dpReceive = new DatagramPacket(buffer, buffer.length);
        client.receive(dpReceive);
        String reqId = new String(dpReceive.getData(), 0 , 8);
        System.out.println("Request ID:"+ reqId);
        
        // Read Object
        ByteArrayInputStream bais = new ByteArrayInputStream(dpReceive.getData(), 8 , dpReceive.getLength() - 8);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Product product = (Product) ois.readObject();
        System.out.println(product);
        
        // process
        String namePd = product.getName();
        List<String> listWord = new ArrayList<>();
        String[] words = namePd .split("\\s+");
        for(String w: words) {
            listWord.add(w);
        }
        int len = words.length;
        
        String firstWord = listWord.get(0);
        String lastWord = listWord.get(len -1);
        listWord.set(0, lastWord);
        listWord.set(len-1 , firstWord);
        String namePdRerversed = String.join(" ", listWord);
        System.out.println(namePdRerversed);
        product.setName(namePdRerversed);
        int quantityPd = product.getQuantity();
        
        StringBuilder sb = new StringBuilder(Integer.toString(quantityPd));
        String quantityPdReversed = sb.reverse().toString();
        product.setQuantity(Integer.parseInt(quantityPdReversed));
        System.out.println(product);
        
        // Write Object
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(product);
        oos.flush();
        
        // Send Data
        byte[] sendData = new byte[8 + baos.size()];
        System.arraycopy(reqId.getBytes(), 0, sendData, 0, 8);
        System.arraycopy(baos.toByteArray(), 0, sendData, 8, baos.size());
        DatagramPacket dpSubmit = new DatagramPacket(sendData, sendData.length, sA,sP);
        client.send(dpSubmit);
        
    }
}
