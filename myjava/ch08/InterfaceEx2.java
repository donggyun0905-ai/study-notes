package ch08;

interface Interface2{
    //필드는 자동적으로 static,final 되기 때문에 반드시 초기화
    int a = 22;
    void prn();

    //일반메소드는 선언 할수 없지만 버전업으로 static, default는 가능
    static void prn2(){System.out.println("static 메소드");}
    default void prn3(){System.out.println("default 메소드");}
}

class MyClass2 implements Interface2{
    @Override
    public void prn() {}
    @Override //선택사항
    public void prn3() {
        System.out.println("Override default 메소드");
    }
}

public class InterfaceEx2 {
    public static void main(String[] args) {
        //Interface2.a = 23;
        Interface2.prn2();//static 메소드 : 인터페이스 이름 접근 사용
        MyClass2 mc = new MyClass2();
        mc.prn3();;
    }
}
