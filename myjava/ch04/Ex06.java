package ch04;

import java.util.Scanner;

public class Ex06 {
	public static void main(String[] args) {
		//60점 이상 합격, 4학년은 70점 이상 합격
		Scanner sc = new Scanner(System.in);
		int score, grade;
		System.out.print("점수를 입력하세요(0~100) : ");
		score = sc.nextInt();
	
		System.out.println("학년을 입력하세요(1~4):");
		grade = sc.nextInt();
		
	if(score>=60) {
		if(grade==4&&score<70) {
			System.out.println("불합격");
		}else {
			System.out.println("합격");
		}
	}else {
		System.out.println("불합격");
	}
	}
	
}