package ch13;

import java.util.Enumeration;
import java.util.Hashtable;

public class HashtableEx1 {
    public static void main(String[] args) {
        Hashtable<String, String> ht = new Hashtable<String,String>();
        //add : 한개의 요소가 저장
        //put : 키 값과 value 한쌍이 저장이 될때 사용
        ht.put("사과","Apple");
        ht.put("포도","Grapes");
        ht.put("딸기","Strawbery");
        ht.put("딸기","ErdBbeern"); //덮어쓰기 : 동일한 키값을 put되면 이렇게 됨
        ht.put("a","1111");
        System.out.println(ht.size());
        System.out.println("---------------");
        //key값으로 value 접근하는 방식의 자료 구조
        Enumeration<String> e = ht.keys();
        //ht는 순서를 보장하지 않는 구조. FIFO 구조는 아님.
        while(e.hasMoreElements()){
            String key = e.nextElement();
            String value = ht.get(key);
            System.out.println(key + ":" + value);
        }
    }
}
