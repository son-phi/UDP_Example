/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpexample;
import java.util.*;
/**
 *
 * @author Lenovo
 */
public class MapPractice {
    public static void main(String[] args) {
        String st = "Phi Quoc Tu Son phi quooc tu son";
        learnMap();
//        demTanSuatXuatHien(st.replace(" ",""));
    }
    
    public static void learnMap(){
        // tao comparator cho key 
        Comparator<String> cmp = new Comparator<String>(){
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        };
        
        Map<String,String> map = new HashMap<>(); // khong co thu tu 
//         Map<String,String> map = new LinkedHashMap<>(); // theo thu tu xuat hien
//         TreeMap<String,String> map = new TreeMap<>(cmp); // theo thu tu tang dan cua key , firstKey(), lastKey(), firstEntry(), headMap(), tailMap()
        // put get size isEmpty Entry remove replace keyset values putall containsKey
        map.put("USA", "Trump");
        map.put("VietNam", "To Lam");
        map.put("China", "Tap Can Binh");
        System.out.println(map.get("US"));
        for(Map.Entry<String, String> entry: map.entrySet()){
            System.out.println(entry.getKey()+" "+ entry.getValue());

        }
        map.remove("US");
        System.out.println(" ");

        map.replace("VietNam", "PMC");
        for(Map.Entry<String, String> entry: map.entrySet()){
            System.out.println(entry.getKey()+" "+ entry.getValue());
        }

        for (String key: map.keySet()){
            System.out.println( key);
        }
//        System.out.println("fist key: " +map.firstKey());
        if (map.containsKey("VietNam"))
            System.out.println("Thu tuong: "+map.get("VietNam"));
    }
    
    public static void demTanSuatXuatHien(String st){
        
        
//        Map<Character,Integer> map = new LinkedHashMap<>();
        TreeMap<Character,Integer> map = new TreeMap<>();
        for (int i = 0;i<st.length() ; i++){
            if (map.containsKey(st.charAt(i))){
                int tanSuat = map.get(st.charAt(i)) +1;
                map.put(st.charAt(i), tanSuat);
            } else {
                map.put(st.charAt(i), 1);
            } 
        }
        for(Map.Entry<Character,Integer> entry: map.entrySet()){
            System.out.println(entry.getKey()+" " + entry.getValue());
        }
    }
}

