package ch06;

class Static2{
    //non-static field
    int a = 0;//반드시 객체를 생성
    //static=filed
    static int b = 0;//클래스명으로 바로 접근 가능
    //상수선언은 일반적으로 static final
    final static int KOREA = 1;


    //non-static.method
    void prn1(){
        System.out.println(a+b);
    }
    //static method
    static void prn2(){
        //a 필드는 반드시 객체를 생성을 해야지만 prn2() 메소드는 객체 생성없이 클래스명으로 사용가능
        //System.out.println(a+b);
    }
}

public class StaticEx2 {
    public static void main(String[] args) {
        Static2 st1 = new Static2();
        Static2 st2 = new Static2();
        st1.a = 10;
        st2.a = 20;
        System.out.println("st1.a: " + st1.a);
        System.out.println("st2.a: " + st2.a);

        st1.b = 100;
        st2.b = 200;

        System.out.println("st1.a: " + st1.b);
        System.out.println("st2.a: " + st2.b);
    }
}
