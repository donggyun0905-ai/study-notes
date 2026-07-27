package ch07;

import java.util.Vector;

public class CastingEx2 {
    public static void main(String[] args) {
        //객체를 담는 자료구조 기능의 클래스
        Vector vec = new Vector();
        String str = new String("하하");
        //저장
        vec.add(str);
        //꺼내기(리턴)
        String str2 = (String)vec.get(0);//FIFO
        System.out.println(str2);
    }
}
