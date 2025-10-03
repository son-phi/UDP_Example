/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpexample;
import java.io.*;
import java.net.*;
import java.util.*;
import UDP.Employee;
/**
 *
 * @author Lenovo
 */
public class UDP_Object_Employee_p476eKla {
    public static void main(String[] args) throws Exception {
        DatagramSocket client = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("203.162.10.109");
        int sP = 2209;
        String code = ";B22DCAT249;p476eKla";
        client.send(new DatagramPacket(code.getBytes(), code.length(), sA, sP));
        
        byte[] buffer = new byte[1024];
        DatagramPacket dpReceive = new DatagramPacket(buffer, buffer.length);
        client.receive(dpReceive);
        
        String reqId = new String(dpReceive.getData(), 0, 8 );
        System.out.println("Request ID: "+ reqId);
        
        ByteArrayInputStream bais = new ByteArrayInputStream(dpReceive.getData(), 8, dpReceive.getLength() -8);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Employee employee = (Employee) ois.readObject();
        System.out.println(employee);
        
        // name
        String[] words = employee.getName().split("\\s+");
        List<String> list = new ArrayList<>();
        for(String w: words){
            list.add(w.substring(0, 1).toUpperCase() + w.substring(1).toLowerCase());
        }
        employee.setName(String.join(" ", list));
        
        // hireDate
        String[] dates = employee.getHireDate().replace("\r", "").split("-");
        String datef = dates[2]+"/" + dates[1]+"/" + dates[0];
        employee.setHireDate(datef);
        
        // salary
        String year = dates[0].toString();
        int sum = 0;
        for (char c : year.toCharArray()){
            sum += c- '0';
        }
        Double salary =  employee.getSalary()*(1 + sum/100.0);
        employee.setSalary(salary);
        System.out.println(employee);
        
        
        ByteArrayOutputStream bois = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bois);
        oos.writeObject(employee);
        oos.flush();
        byte[] sendData = new byte[8 + bois.size() ];
        System.arraycopy(reqId.getBytes(), 0, sendData, 0, 8);
        System.arraycopy(bois.toByteArray(), 0, sendData, 8, bois.size());
        client.send(new DatagramPacket(sendData, sendData.length, sA, sP));
        client.close();
        
    }
}
