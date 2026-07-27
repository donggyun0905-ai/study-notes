package ch06;

import java.util.Locale;

//Car2 타입은 참조형 + 자바 기본형
class Car2{

    void stop(){
        velocity = 0;
    }
    //객체가 생성될 때 순서는 필드가 먼저 생김 -> 메소드 생김 <- 그래서 필드가 밑에 선언이 되어도 에러 발생이 안된다.
    int velocity;

    void test(){
        //프로그램은 위에 선언한 변수를 밑에 사용가능 그 반대는 안됨
        int a =10;
        int b = a;
    }
}

public class CarEx2 {
    public static void main(String[] args) {
        int a =10;
        int b = a;//call by value(기본형 8개 : 값이 복사)
        a = 15;
        System.out.println(a+b);//sum = 25

        Car2 c1 = new Car2();
        Car2 c2 = new Car2();
        c1.velocity = 100;
        c2.velocity = 200;

        System.out.println(c1 /*클래스명 @객체주소*/);
        System.out.println(c2);
        //c1이 레퍼런스된 객체를 자동적으로 gc가 일어남.(garbage collection)
        //c2가 가르키고 있는 객체를 c1도 가르킴.
        c1 = c2;//call by reference
        System.gc();
        System.out.println(c1);
        System.out.println(c2);

        System.out.println(c1.velocity + c2.velocity);

        System.out.println("--------------------------");
        for(int i =0;i<10;i++){
            Car2 c3 = new Car2();
            System.out.println(c3);
        }
        System.out.println("--------------------------");

        String str = new String("efwefgwegwerger");
        System.out.println(str.toUpperCase());
        System.out.println(str.length());
        System.out.println(str.replace("e","x"));

        //객체 생성 없이도 클래스명으로 메소드 및 필드 사용가능.
        System.out.println(Integer.toBinaryString(32)); // 10진수 32를 2진수 리턴
        System.out.println(Math.PI);
    }
}
