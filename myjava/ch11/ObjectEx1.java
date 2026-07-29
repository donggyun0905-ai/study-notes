package ch11;

import java.lang.*;
import java.util.SequencedSet;

class Point1{}

public class ObjectEx1 {
    public static void main(String[] args) {
        Point1 p1 = new Point1();
        Point1 p2 = new Point1();
        //hashCode: 객체를 구분하는 값(메모리 주소값 참조)
        System.out.println("클래스 이름: " + p1.getClass());
        System.out.println(p1.hashCode());
        System.out.println(p2.hashCode());
        System.out.println("객체의 기본정보 : " + p1.toString());
        System.out.println("객체의 기본정보 : " + p2/*toString()생략가능*/);
        //==는 참조형은 객체의 주소값 비교
        System.out.println(p1==p2);//주소값 비교
        System.out.println(p1.equals(p2));//객체가 가지고 있는 값을 비교
        p1=p2;//p2를 레퍼런스 하고 있는 객체를 p1에 대입
        System.out.println(p1==p2);//true
        System.out.println(p1.equals(p2));//true
    }
}

