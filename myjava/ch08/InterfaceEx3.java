package ch08;

interface Interface3{
    void prn1();
}

//클래스는 다중상속이 불가능
//class YFrame extends Object, Frame{}

interface Interface3_1 extends  Interface3{
    void prn2();
}

interface Interface3_2{
    void prn3();
}

//인터페이스끼리는 다중 상송이 가능하다.
interface Interface3_3 extends Interface3,Interface3_2{
    void prn3();
}

public class InterfaceEx3 {
    public static void main(String[] args) {

    }
}
