/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpexample;
import java.io.*;
import java.net.*;
import java.util.*;
import UDP.Book;
/**
 *
 * @author Lenovo
 */
public class UDP_Object_Book_GCVa8wLq  {
    public static void main(String[] args) throws Exception {
        DatagramSocket client = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("203.162.10.109");
        int sP = 2209;
        String code = ";B22DCAT249;GCVa8wLq";
        client.send(new DatagramPacket(code.getBytes(), code.length(), sA, sP));
        
        byte[] buffer = new byte[1024];
        DatagramPacket dpReceive = new DatagramPacket(buffer,  buffer.length);
        client.receive(dpReceive);
        
        String reqId = new String(dpReceive.getData(),0,8);
        System.out.println("Request ID: "+reqId);
        
        ByteArrayInputStream bais = new ByteArrayInputStream(dpReceive.getData(), 8, dpReceive.getLength() -8);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Book book = (Book) ois.readObject();
        System.out.println(book);
        
        // title
        String title = book.getTitle();
        String[] titleW = title.split("\\s+");
        String titleF = titleW[0].substring(0,1).toUpperCase() + titleW[0].substring(1).toLowerCase() ;
        for (int i = 1; i< titleW.length; i++){
            titleF += " "+ titleW[i].substring(0,1).toUpperCase() + titleW[i].substring(1).toLowerCase() ;
        }
        
        book.setTitle(titleF);
        
        // author
        String[] authorW = book.getAuthor().split("\\s+");
        int lenA = authorW.length;
        String author = authorW[0].toUpperCase()+ "," ;
        for(int i = 1 ; i< authorW.length ; i++){
            author+= " "+authorW[i].substring(0, 1).toUpperCase() + authorW[i].substring(1).toLowerCase();
        }
        book.setAuthor(author);
        
        //isbn
        String isbnO = book.getIsbn();
        String isbnF = "";
        for (int i = 0 ; i<isbnO.length();i++){
            isbnF += isbnO.charAt(i);
            if (i == 2 || i== 3 || i ==5 ||i == 11 ) isbnF+="-";
        }
        book.setIsbn(isbnF);
        
        
        // publishDate
        String[] dateW = book.getPublishDate().replace("\r", "").split("-");
        
        String dateF = dateW[1]+"/" + dateW[0];
        book.setPublishDate(dateF);
        
        System.out.println(book);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(book);
        oos.flush();
        
        byte[] sendData = new byte[8+baos.size()];
        System.arraycopy(reqId.getBytes(), 0, sendData, 0, 8);
        System.arraycopy(baos.toByteArray(), 0, sendData, 8, baos.size());
        client.send(new DatagramPacket(sendData, sendData.length, sA,sP));
        client.close();
    }
}
