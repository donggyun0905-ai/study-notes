package ch06;

class Method1{
    //method : 객체의 반복적인 기능, 최대한 세분화
    public/*제어자*/ int/*리턴타입*/ abs/*메소드 형*/(int num/*매개변수*/)/*선언부*/{
        if(num<0)
            num=-num;
        return num;
    }
    void prn(int a, int b) {
        System.out.println(a+"+" +b+ "=" + (a+b));
    }
}

public class MethodEx1 {
    public static void main(String[] args) {
        Method1 mt = new Method1();
        int n = mt.abs(-23);//리턴타입이있는 메소드는 반드시 값을 리턴해야하는 강제성은 없다.
        System.out.println(n);
        mt.prn(10,20);
        int a =100;
        int b = 200;
        int c =Math.max(1,2);
    }
}
