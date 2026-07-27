package ch06;

/*static(고정된, 정적인):필드,메소드,class(조건). 객체에 속하지 않고 클래스에 고정된 개념
* 1. 필드, 메소드 : 객체 생성 없이 클래스명으로 JVM이 메모리에 로딩하여 사용가능.
* 2. 클래스: 일반적인 불가능, 내부 클래스 앞에만 사용가능
* 3. 필드는 모든 객체가 공유 할 수 있도록 단 한개만 만들어짐.
* */

/*static*/class Static1{
    static int a = 10;
    int b = 20;

    //클래스 앞에 static 오는 경우는 없다 <- 틀린것
    static class Inner{}

    static void prn1(){
        System.out.println(a);
    }
    static void prn2(){
        //System.out.println(a+b);
    }
}

public class StaticEx1 {
    public static void main(String[] args) {
        Static1 st1 = new Static1();
        st1.a = 20;
        st1.b = 20;
        st1.prn1();
        st1.prn2();
        //객체 생성없이 가능한 필드 및 변수
        Static1.a=30;
        Static1.prn1();
        //Math클래스의 모든 필드와 메소드 static why <- math객체는 객체로서 의미가 없음
        Math.abs(-10);
        //문제 "40"을 숫자로 변환
        int c = Integer.parseInt("40");
        System.out.println(c);

    }
}
