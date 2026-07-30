package ch13;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class ArrayListEx1 {
    public static void main(String[] args) {
        //AllayList 는 동기화 기능이 없다. vector 동기화 기능
        ArrayList<String> list = new ArrayList<String>();
        list.add("짜장면");
        list.add("짬뽕");
        list.add("우동");
        list.add(2,"탕수육");
        list.add(3,"탕수육");
        System.out.println("----------");
        for (int i =0;i<list.size();i++) {
            System.out.println((i+1) + "." + list.get(i));
        }
        System.out.println("----------");
        //순차적으로 가져오고 모든 Collection 객체의 동적바인딩의 역할로 사용가능
        Iterator<String> e = list.iterator();
        while(e.hasNext()){
            System.out.println(e.next());
        }

        Vector<String> list2 = new Vector<String>();
        list2.add("Java");
        Iterator<String> e2 = list2.iterator();
        while(e2.hasNext()){
            System.out.println(e.next());
        }
    }
}
