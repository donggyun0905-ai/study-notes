package ch03;

public class Ex4 {
    public static void main(String[] args) {
        int a = 10,b =4, c=0;
        System.out.println((a==b)&(a>b));
        System.out.println((a==b)&&(a>b));
        //System.out.println((a==b)&(a/c==b));
        System.out.println((a==b)&&(a/c==b));
        System.out.println("*********************");
        System.out.println((a!=b)|(a<b));
        System.out.println((a!=b)||(a<b));
        System.out.println("*********************");
        System.out.println(true^false);
        System.out.println(false^true);
        System.out.println(true^true);
        System.out.println(false^false);
    }
}
