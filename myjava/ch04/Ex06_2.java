package ch04;

import java.util.Scanner;

public class Ex06_2 {
	public static void main(String[] args) {
		/*점수와 학년을 입력을 받아서 60이상이면 합격인데, 
		 * 4학년은 70점 이상 합격이다.*/

        Scanner sc = new Scanner(System.in);
        int score, grade;

        System.out.print("점수를 입력하세요(0~100): ");
        score = sc.nextInt();
        
        System.out.print("학년을 입력하세요(1~4): ");
        grade = sc.nextInt();

        switch (grade) {
            case 1, 2, 3:
                if(score >= 60){
                    System.out.printf("%d학년 %d점 합격입니다", grade, score);
                }
                else{
                    System.out.println("불합격입니다");
                }
                break;
            case 4:
                if(score >= 70){
                    System.out.printf("%d학년 %d점 합격입니다", grade, score);
                }
                else{
                    System.out.println("불합격입니다");
                }
                break;
        }
	}
}
