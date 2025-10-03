/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpexample;
import java.net.*;
import java.util.*;
import java.io.*;
import UDP.Student;
/**
 *
 * @author Lenovo
 */
public class UDP_Object_Student_i6bmCBPN {
    public static void main(String[] args) throws Exception {
        DatagramSocket client = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("203.162.10.109");
        int sP = 2209;
        String code = ";B22DCAT249;i6bmCBPN";
        DatagramPacket dpSend = new DatagramPacket(code.getBytes(), code.length(), sA, sP);
        client.send(dpSend);
        
        byte[] buffer= new byte[1024];
        DatagramPacket dpReceive = new DatagramPacket(buffer, buffer.length);
        client.receive(dpReceive);
        
        String reqId = new String(dpReceive.getData(), 0 , 8);
        System.out.println("Request Id:" + reqId);
        
        ByteArrayInputStream bais = new ByteArrayInputStream(dpReceive.getData(), 8 , dpReceive.getLength() - 8);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Student student = (Student) ois.readObject();
        System.out.println(student);
        
        String[] nameWords = student.getName().split("\\s+");
        List<String> listWord = new ArrayList<>();
        String[] arrWord = new String[nameWords.length];
        int i =0;
        for (String w : nameWords) {
            //name
            String nameFormatted = w.substring(0,1).toUpperCase() + w.substring(1).toLowerCase();
            listWord.add(nameFormatted);
            
            //email
            String emailFormatted = w.substring(0).toLowerCase();
            arrWord[i]= emailFormatted;
            i++;
        }
        student.setName(String.join(" ", listWord));
        
        //email
        String email = arrWord[nameWords.length-1];
        for (i=0 ; i< arrWord.length-1; i++ ) {
            email+= arrWord[i].substring(0, 1);
        }
        email += "@ptit.edu.vn";
        student.setEmail(email);
        System.out.println(student);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(student);
        oos.flush();
        
        byte[] sendData = new byte[8 + baos.size()];
        System.arraycopy(reqId.getBytes(), 0, sendData, 0, 8);
        System.arraycopy(baos.toByteArray(), 0, sendData, 8, baos.size());
        DatagramPacket dpSubmit = new DatagramPacket(sendData, sendData.length, sA, sP);
        client.send(dpSubmit);
        client.close();
    }
}
