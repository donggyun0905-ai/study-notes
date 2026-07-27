package ch04;

import java.util.Scanner;

public class Ex4 {
    public static void main(String[] args) {
        /*Month(1~12)입력을 하면 각각
         * 봄 : 3~5
         * 여름 : 6 ~8
         * 가을 : 9~11
         * 겨울 : 12 ~2
         * 5 입력하면 '봄입니다'
         * 만약 범위가 벗어나면 '해당되는 계절이 없습니다'
         * if-else, switch (case 1,2,3) 각각 구현
         * */

        Scanner sc = new Scanner(System.in);
        System.out.print("month를 입력하세요 : ");
        int input = sc.nextInt();

        switch (input){
            case 3,4,5 :
                System.out.println("봄");
                break;
            case 6,7,8:
                System.out.println("여름");
                break;
            case 9,10,11:
                System.out.println("가을");
                break;
            case 12,1,2:
                System.out.println("겨울");
                break;
            default:
                System.out.println("해당되는 계절이 없습니다.");
        }

        if(3<= input && input <=5){
            System.out.println("봄");
        }
        else if(6<= input && input <= 8){
            System.out.println("여름");
        }
        else if(9 <= input && input <= 11){
            System.out.println("가을");
        }
        else if(12 == input || (1 <= input && input <= 2)){
            System.out.println("겨울");
        }
        else{
            System.out.println("해당되는 계절이 없습니다.");
        }
    }
}
