package ch02;
import java.util.Scanner;

public class Ex8 {
    public static void main(String[] args) {
        int a =10;
        int b=23;
        Scanner sc = new Scanner(System.in);
        System.out.print("이름 : ");
        String name = sc.nextLine();
        System.out.println("나이 : ");
        int age = sc.nextInt();
        System.out.printf("\n %s님의 나이는 %d살 입니다.",name,age);
    }
}
