package ch03;

public class Ex6 {
    public static void main(String[] args) {
        int a =10;
        if(a%2 == 0)System.out.println("짝수");
        else System.out.println("홀수");
        String str = (a%2==0)?"짝수":"홀수";
        System.out.println((a%2==0)?"짝수":"홀수");

        int b = Math.min(10,20);
        System.out.println(b);
        int c= 10;
        int d = 20;
        int e = d> c? d:c;
        System.out.println(e);
    }
}
