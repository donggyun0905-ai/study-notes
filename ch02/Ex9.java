package ch02;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Ex9 {
    public static void main(String[] args) throws Exception{
        /*스캐너와 무한반복 활용해 사용자가 o을 입력할 때 까지 숫자
        * 계속 받아 짝/홀 판별하고 o입력 시 프로그램 종료하는 코드 작성*/
        Scanner sc = new Scanner(System.in);

        while(true) {
            System.out.print("입력하세요 : ");
            int input;
            try {
                input = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("숫자만 입력해주세요.");
                sc.next();
                continue;
            }
            if(input == 0){
                System.out.println("종료합니다.");
                break;
            }
            else if (input % 2 == 0) {
                System.out.println("짝수");
                continue;
            }
            System.out.println("홀수");
        }
    }
}
