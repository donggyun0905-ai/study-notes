package ch02;
public class Ex3 {
    public static void main(String[] args) {
        byte b = 10;
        short s = 20;
        int i = 30;
        long l = 40;
        System.out.println(Short.MAX_VALUE);
        short s1 = (short)32768;
        System.out.println(s1);

        char c = '가';
        String str = "java";
        System.out.println(str.length());

        boolean bl = false;

        System.out.println(Byte.MAX_VALUE+"~"+Byte.MIN_VALUE);
        System.out.println(Byte.MIN_VALUE+"~"+Double.MIN_VALUE);

        int i1 = 200;
        long l1 = 200;

        long l2 = 300;
        int i2 = (int)l2;
    }
}
