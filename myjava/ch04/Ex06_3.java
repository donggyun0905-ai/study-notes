package ch04;

import java.util.Scanner;

public class Ex06_3 {
	public static void main(String[] args) {
		/*점수와 학년을 입력을 받아서 60이상이면 합격인데, 
		 * 4학년은 70점 이상 합격이다.*/
        Scanner sc = new Scanner(System.in);
        int score, grade;
        boolean accept = false;
        while (true) {
            System.out.print("점수를 입력하세요(0~100) : ");
            score = sc.nextInt();
            if (!(score >= 0 && score <= 100)) {
                System.out.println("0 ~ 100 까지의 정수를 입력하세요.");
                continue;
            }
            
            System.out.print("학년을 입력하세요(1~4) : ");
            grade = sc.nextInt();
            if (!(grade >= 1 && grade <= 4)) {
                System.out.println("1 ~ 4 까지의 정수를 입력하세요.");
                continue;
            }
            
            if (score >= 70) {
                accept = true;
            }else if (score >= 60) {
                if (grade != 4) {
                    accept = true;
                }
            }
            break;
        }
        

        System.out.println(accept ? "합격입니다." : "불합격입니다.");
	}
}
