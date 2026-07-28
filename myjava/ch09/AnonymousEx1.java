package ch09;

//익명 클래스는 추상클래스와 인터페이스를 사용하기위한 목적으로 제공

abstract class MyAbst1{
    abstract void prn();
}

interface MyInter2{
    void prn1();
}
//일반적인 상속 및 구현 형태
class A extends MyAbst1{
    @Override
    void prn() {

    }
}
class B implements MyInter2{
    @Override
    public void prn1() {

    }
}

public class AnonymousEx1 {
    public static void main(String[] args) {
        //익명클래스 문법으로 추상클래스, 인터페이스 선언하고 실행하는 방법
        A a = new A();
        a.prn();
        B b = new B();
        b.prn1();
        ////////////////////////////
        new MyAbst1(){
            @Override
            void prn() {
                System.out.println("추상 익명 클래스 호출");
            }
        }/*선언 생성*/.prn();
        new MyInter2(){
            @Override
            public void prn1() {
                System.out.println("인터페이스 익명 클래스 호출");
            }
        }/*선언 생성*/.prn1();
    }
}
