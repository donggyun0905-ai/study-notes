package ch03;

import java.util.Scanner;

public class Ex5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("숫자를 입력하세요 : ");
            int a = sc.nextInt();
            if(a != 0 && a%3==0){
                System.out.println(a + "는 3의 배수입니다.");
            }else{
                System.out.println(a + "는 3의 배수가 아닙니다.");
            }
        }
    }
}
