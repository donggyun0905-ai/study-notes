package ch04;

class A3{

}

public class Ex3 {
    public static void main(String[] args) {
        int i = 10;
        int j = i;
        i = 11;
//////////////////////////////////////////
        A3 a = new A3();
        A3 b = new A3();
        System.out.println(a);
        System.out.println(b);
        System.out.println(a==b);

    }
}
