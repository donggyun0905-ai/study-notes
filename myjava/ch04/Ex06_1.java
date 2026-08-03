package ch04;

import java.util.Scanner;

public class Ex06_1 {

    public static void main(String[] args) {
        // 점수와 학년을 입력을 받아서 60 이상이면 합격인데,
        // 단, 4학년은 70점 이상이어야 합격이다.

        Scanner sc = new Scanner(System.in);
        int score, grade;

        System.out.println("점수 입력(0~100): ");
        score = sc.nextInt();

        System.out.println("학년 입력(1~4): ");
        grade = sc.nextInt();

        if (score < 0 || score > 100) {
            System.out.println("잘못된 입력");
        } else if (grade == 4) {
            System.out.println((score >= 70) ? "합격" : "불합격");
        } else if (grade < 4 && grade > 0) {
            System.out.println((score >= 60) ? "합격" : "불합격");
        } else {
            System.out.println("잘못된 입력");
        }
    }
}